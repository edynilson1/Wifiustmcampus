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
## Screenshots da implementação

### 1. MainActivity
Implementação da tela principal da aplicação.

![MainActivity](https://github.com/user-attachments/assets/c456d00f-07c6-4716-8201-bc45e848dafa)

### 2. Permissões
Configuração das permissões de Wi-Fi e localização.

![Permissões](https://github.com/user-attachments/assets/0c712947-cd98-46dd-82f9-ca409a712d4d)

### 3. Botão de pesquisa
Implementação do botão que inicia a pesquisa.

![Botão de pesquisa](https://github.com/user-attachments/assets/db27cb99-354d-433a-b6a4-6d00ba87ad24)

### 4. Intent
Implementação da comunicação entre as Activities.

![Intent](https://github.com/user-attachments/assets/4c2e624f-adb6-4f6d-954f-8a3730305e9a)

### 5. Pesquisa Wi-Fi
Implementação da pesquisa das redes Wi-Fi.

![Pesquisa Wi-Fi](https://github.com/user-attachments/assets/eb3680fc-5d19-4188-b1f2-c7dbb33fd675)

### 6. WifiManager
Utilização do WifiManager para acessar o Wi-Fi.

![WifiManager](https://github.com/user-attachments/assets/72639056-6cf9-44e9-b892-dbb8258e7493)

### 7. BroadcastReceiver
Receção dos resultados da pesquisa Wi-Fi.

![BroadcastReceiver](https://github.com/user-attachments/assets/b8a053b3-c6f2-4822-be90-6df670516d4f)

### 8. Intensidade da rede
Obtenção do nível de sinal RSSI.

![Intensidade da rede](https://github.com/user-attachments/assets/96ae1f63-fb42-4e68-b66d-d34a4afed346)

### 9. SSID
Obtenção do nome da rede Wi-Fi.

![SSID](https://github.com/user-attachments/assets/44ef84b2-924c-435b-9315-68a886ff64c9)

### 10. RecyclerView
Implementação da lista para apresentar as redes.

![RecyclerView](https://github.com/user-attachments/assets/f74be483-023c-49d0-8e68-e0b69252c7b9)

### 11. WifiAdapter
Implementação do Adapter para apresentar os dados.

![WifiAdapter](https://github.com/user-attachments/assets/43ecf6a8-57ed-4bfe-995d-8131e8c7d048)

### 12. Lista de resultados
Apresentação das redes encontradas.

![Lista de resultados](https://github.com/user-attachments/assets/4f26e828-318f-4a4a-a6c9-5caede110370)

### 13. ResultsActivity
Apresentação final das redes encontradas.

![ResultsActivity](https://github.com/user-attachments/assets/9e527528-556b-4cc0-9af9-429a21691668)

