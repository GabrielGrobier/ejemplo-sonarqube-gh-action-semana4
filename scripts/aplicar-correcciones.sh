#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cp "$ROOT/material-docente/CalculoMultaService_CORREGIDO.java.txt" \
   "$ROOT/src/main/java/cl/duoc/biblioteca/CalculoMultaService.java"
echo "Correcciones aplicadas. Revisa con: git diff"
