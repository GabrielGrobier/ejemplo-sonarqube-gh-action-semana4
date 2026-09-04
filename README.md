# Biblioteca Demo: GitHub Actions + SonarQube en EC2

Este proyecto reproduce el mismo concepto trabajado con Jenkins, pero usando **GitHub Actions** como motor de CI.

```text
push a main
    |
    v
GitHub Actions (ubuntu-latest)
    |
    +--> checkout
    +--> Java 21
    +--> Maven clean verify
    +--> SonarScanner for Maven
                |
                v
          SonarQube en EC2
```

## 1. Subir este proyecto a GitHub

Crea un repositorio y sube el contenido de esta carpeta dejando `.github/workflows/sonarqube.yml` en la raíz.

## 2. Configurar SonarQube

En el SonarQube desplegado en EC2 genera un token:

```text
My Account -> Security -> Generate Tokens
```

## 3. Configurar GitHub

En el repositorio:

### Secret

```text
Settings -> Secrets and variables -> Actions -> Secrets
```

Crear:

```text
SONAR_TOKEN = squ_xxxxxxxxxxxxxxxxx
```

### Variable

```text
Settings -> Secrets and variables -> Actions -> Variables
```

Crear:

```text
SONAR_HOST_URL = http://IP_PUBLICA_O_DNS_EC2:9000
```

La URL debe ser accesible desde Internet porque el workflow usa `ubuntu-latest`, es decir, un runner hospedado por GitHub. Para una demo rápida puede usarse el puerto 9000, pero si el servidor quedará publicado de forma persistente usa HTTPS antes de enviar tokens desde GitHub Actions.

## 4. Ejecutar

El workflow se ejecuta automáticamente con push a `main`, o manualmente desde:

```text
GitHub -> Actions -> Build y análisis SonarQube -> Run workflow
```

## 5. Qué hace el workflow

```yaml
mvn -B clean verify \
  org.sonarsource.scanner.maven:sonar-maven-plugin:5.7.0.6970:sonar
```

Esto compila y prueba el backend y luego ejecuta el análisis mediante SonarScanner for Maven.

## 6. Hallazgos intencionales

`CalculoMultaService.java` contiene malas prácticas intencionales para que SonarQube pueda mostrar hallazgos, por ejemplo:

- credencial hardcodeada;
- `System.out.println` dentro del servicio;
- comparación de `String` con `==`;
- `new String(...)` innecesario;
- excepción capturada e ignorada.

Después del primer análisis:

```bash
./scripts/aplicar-correcciones.sh
git diff
git add .
git commit -m "Corrige hallazgos detectados por SonarQube"
git push
```

El push vuelve a ejecutar GitHub Actions y permite comparar el análisis anterior con el nuevo.

## 7. Probar funcionalmente el backend (opcional)

```bash
docker compose -f docker-compose.local.yml up -d --build
```

Endpoints:

```text
GET http://localhost:8081/api/prestamos
GET http://localhost:8081/api/prestamos/1/resumen
```

Detener:

```bash
docker compose -f docker-compose.local.yml down -v
```

## Flujo pedagógico Jenkins vs GitHub Actions

```text
Jenkins                        GitHub Actions
-------                        --------------
Freestyle Project              workflow YAML
Git checkout                   actions/checkout
Execute shell                  run:
mvn clean install              mvn clean verify
SonarQube Scanner step         SonarScanner for Maven
Jenkins Credential             GitHub Secret
SonarQube Server URL           SONAR_HOST_URL
Build Now                      workflow_dispatch / push
Console Output                 Actions logs
```
