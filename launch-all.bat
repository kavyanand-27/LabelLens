@echo off
title LabelLens - Multilingual Food Ingredient Platform
echo ============================================================
echo           STARTING LABELLENS PLATFORM (ZERO-COST)
echo ============================================================
echo.

set PORT=8080
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :%PORT% ^| findstr LISTENING') do taskkill /F /PID %%a >nul 2>&1

echo [1/2] Starting Spring Boot Backend (Port 8080)...
start "LabelLens Backend" java -jar "%~dp0backend\target\labellens-backend-1.0.0.jar"

echo [2/2] Starting React Vite Frontend (Port 3000)...
cd /d "%~dp0frontend"
start "LabelLens Frontend" npm run dev -- --host 127.0.0.1

echo.
echo ============================================================
echo LabelLens is launching!
echo Backend API : http://localhost:8080
echo Frontend Web: http://127.0.0.1:3000
echo ============================================================
echo.
