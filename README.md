# WiFi Campus Scanner

## Turma
3L6LASIR1T

## Tema
Tema A — Teste de Cobertura Wi-Fi

## Objetivo
Aplicação Android desenvolvida em Java para pesquisar redes Wi-Fi próximas, filtrar pelo nome/SSID e apresentar a intensidade do sinal em RSSI (dBm).

## Funcionalidades
- Pesquisa real de redes Wi-Fi próximas.
- Filtro por nome ou parte do SSID.
- Apresentação de SSID e RSSI.
- Classificação do sinal: Excelente, Bom, Regular ou Fraco.
- Resultados ordenados da maior para a menor intensidade.
- Duas Activities ligadas por Intent.
- Interface baseada em ConstraintLayout e Widgets.

## Activities
### MainActivity
Recebe o nome ou parte do nome da rede e solicita as permissões necessárias. Depois usa Intent para abrir ResultsActivity.

### ResultsActivity
Utiliza WifiManager e BroadcastReceiver para realizar/receber uma pesquisa real de redes Wi-Fi e apresenta os resultados num RecyclerView.

## Permissões
- ACCESS_WIFI_STATE
- CHANGE_WIFI_STATE
- ACCESS_FINE_LOCATION
- ACCESS_COARSE_LOCATION
- NEARBY_WIFI_DEVICES (Android 13+)

As permissões são solicitadas em tempo de execução quando necessário.

## Tecnologias
- Android Studio
- Java
- Android SDK
- ConstraintLayout
- RecyclerView
- WifiManager
- BroadcastReceiver
- Intent

## Como executar
1. Abrir o projeto no Android Studio.
2. Sincronizar o Gradle.
3. Executar num smartphone Android com Wi-Fi.
4. Ativar Wi-Fi e, quando solicitado, conceder as permissões.
5. Introduzir um SSID ou deixar o campo vazio.
6. Pressionar PESQUISAR WI-FI.

## Teste de cobertura
Para demonstrar o objetivo do trabalho, testar a mesma rede em diferentes pontos do campus, por exemplo:
- Sala de aula
- Biblioteca
- Cantina
- Corredor

Registar o RSSI obtido em cada ponto e comparar os resultados.

## Integrantes
1. António Mahacha - 202400714
2. Edynilson Ruben - 202400944
3. Gerson Paulino - 202401448
4. Shelseo de Abreu - 202400673
5. Wesley Mulhanga- 202400712

## Screenshots