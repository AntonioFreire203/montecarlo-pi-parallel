@echo off
echo ===================================================
echo Testes de Desempenho - Calculo de Pi (Monte Carlo)
echo ===================================================

set POINTS=100000000
set THREADS=4

echo.
echo Compilando todos os arquivos...
javac PiSequential.java
javac PiParallel.java
javac PiServer.java
javac PiClient.java
echo Compilacao concluida!

echo.
echo ===================================================
echo Executando versao SEQUENCIAL com %POINTS% pontos
echo ===================================================
echo.
java PiSequential %POINTS%

echo.
echo ===================================================
echo Executando versao PARALELA com %POINTS% pontos e %THREADS% threads
echo ===================================================
echo.
java PiParallel %POINTS% %THREADS%

echo.
echo ===================================================
echo Para executar a versao DISTRIBUIDA:
echo.
echo 1. Execute o servidor em uma janela separada:
echo    java PiServer %POINTS%
echo.
echo 2. Execute os clientes em outras janelas ou maquinas:
echo    java PiClient [endereco_do_servidor]
echo.
echo Para testes locais, use 'localhost' como endereco do servidor.
echo ===================================================

pause
