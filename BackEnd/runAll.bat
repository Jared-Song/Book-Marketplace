@echo off
for /d %%i in (%~dp0\*) do (
	cd %%i
	if exist %%~fi\target\*.jar start cmd /C "mvnw package && cd %%~fi\target\ && for /f "delims=" %%x in ('dir /b *.jar') do java -jar %%x"
)