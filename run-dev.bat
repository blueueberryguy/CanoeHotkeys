@echo off
pushd "%~dp0"
call gradlew.bat run
set EXITCODE=%ERRORLEVEL%
popd
exit /b %EXITCODE%
