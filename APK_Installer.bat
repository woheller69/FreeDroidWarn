@echo off
setlocal enabledelayedexpansion

:: Set ADB path (assuming it's in Downloads)
set "adb_path=%USERPROFILE%\Downloads\adb.exe"

:: Check if adb exists
if not exist "%adb_path%" (
    echo Error: adb.exe not found in %USERPROFILE%\Downloads
    echo Please place adb.exe in your Downloads folder and try again.
    pause
    exit /b 1
)

:: Check if any APK files exist
set "apk_count=0"
for %%f in ("%USERPROFILE%\Downloads\*.apk") do (
    set /a apk_count+=1
)

if %apk_count% equ 0 (
    echo No APK files found in Downloads folder.
    pause
    exit /b 1
)

echo Available APK files:
echo ====================
set "counter=1"
for %%f in ("%USERPROFILE%\Downloads\*.apk") do (
    echo !counter!. %%~nxf
    set "apk_!counter!=%%f"
    set /a counter+=1
)
echo.
echo 0. Exit

:menu
echo.
set /p choice="Select APK to install (0-%apk_count%): "

if "%choice%" equ "0" (
    echo Goodbye!
    exit /b 0
)

:: Validate selection
set "valid=false"
for /l %%i in (1,1,%apk_count%) do (
    if "%choice%" equ "%%i" set "valid=true"
)
if "%valid%" equ "false" (
    echo Invalid selection. Please try again.
    goto menu
)

:: Install selected APK
echo Installing APK...
set "selected_apk="
for /l %%i in (1,1,%apk_count%) do (
    if "%choice%" equ "%%i" (
        set "selected_apk=!apk_%%i!"
        goto install_apk
    )
)
:install_apk
if defined selected_apk (
    echo Installing: %selected_apk%
    "%adb_path%" install "%selected_apk%"
    if %errorlevel% equ 0 (
        echo.
        echo Successfully installed!
    ) else (
        echo.
        echo Installation failed!
    )
) else (
    echo Error: Could not find selected APK file
)
pause
goto menu

