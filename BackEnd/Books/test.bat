@echo off
echo ASDOAIWHEDUYAHBWJKEBJKABEWAB
cd %~dp0\target && for /f "delims=" %%x in ('dir /b *.jar') do java -jar %%x
echo %latestjar% hah
pause