@echo off
:: ==========================================
:: ☕ Cafetería JavaFX - Compilación y ejecución automática
:: ==========================================

setlocal
set ROOT=%~dp0
set JAVAFX_LIB=%ProgramFiles%\Java\javafx-sdk-25.0.1\lib

:: Verifica que Java esté disponible
where java >nul 2>nul
if %errorlevel% neq 0 (
echo ❌ Java no está instalado o no está en el PATH.
pause
exit /b
)

echo ⚙️ Compilando clases desde /src ...
if not exist "%ROOT%bin" mkdir "%ROOT%bin"

javac -d "%ROOT%bin" -cp "%ROOT%lib/*" --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml "%ROOT%src\com\cafe\ui\MainApp.java"
if %errorlevel% neq 0 (
echo ❌ Error al compilar MainApp.java
pause
exit /b
)

echo 🚀 Iniciando Cafetería JavaFX...
echo.

java --module-path "%JAVAFX_LIB%" ^
--add-modules javafx.controls,javafx.fxml ^
-cp "%ROOT%bin;%ROOT%lib/*" com.cafe.ui.MainApp

if %errorlevel% neq 0 (
echo.
echo ❌ Error al iniciar la aplicación.
echo Verificá que JavaFX esté instalado y que MainApp.java compile correctamente.
)

pause
endlocal
