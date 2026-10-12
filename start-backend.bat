@echo off
set PORT=8080
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :%PORT% ^| findstr LISTENING') do taskkill /F /PID %%a >nul 2>&1
echo Port %PORT% freed. Starting LabelLens Backend...
java -jar c:\Users\HP\Documents\PROTO\backend\target\labellens-backend-1.0.0.jar
