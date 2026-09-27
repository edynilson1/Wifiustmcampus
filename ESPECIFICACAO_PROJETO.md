# Especificação do Projeto — WiFi Campus Scanner

## 1. Objetivo
Desenvolver uma aplicação Android em Java capaz de pesquisar redes Wi-Fi disponíveis nas proximidades e permitir que o utilizador filtre os resultados pelo nome da rede. A aplicação serve como uma ferramenta simples para comparar a cobertura Wi-Fi em diferentes pontos do campus.

## 2. Funcionalidades
- Introdução do nome completo ou parcial de uma rede.
- Solicitação das permissões necessárias.
- Pesquisa real através do Wi-Fi do dispositivo.
- Listagem de SSID e RSSI.
- Filtro por SSID.
- Ordenação pela intensidade do sinal.
- Classificação qualitativa do sinal.
- Navegação entre duas Activities.

## 3. Activities
### MainActivity
É a Activity inicial. Contém TextViews, TextInputEditText e Button. O utilizador escreve o nome da rede e inicia a pesquisa.

### ResultsActivity
Apresenta os resultados num RecyclerView. A pesquisa é realizada usando WifiManager e os resultados são recebidos através de BroadcastReceiver.

## 4. Navegação
MainActivity -> Intent -> ResultsActivity.

O texto introduzido é enviado através de `putExtra("FILTER", filter)` e recuperado com `getIntent().getStringExtra("FILTER")`.

## 5. Funcionalidade de rede
A funcionalidade real utiliza `WifiManager.startScan()` e `WifiManager.getScanResults()`. Cada resultado contém, entre outros dados, o SSID e o nível do sinal RSSI em dBm.

## 6. Permissões
São declaradas as permissões relacionadas com Wi-Fi e localização/nearby devices. A aplicação solicita permissões em tempo de execução conforme a versão do Android.

## 7. Interface
As telas utilizam ConstraintLayout. São utilizados TextView, Button, TextInputEditText, RecyclerView e outros componentes Android.

## 8. Teste prático
A aplicação deve ser testada em vários pontos do campus. Para cada ponto, procurar a rede da universidade e registar o RSSI. Quanto mais próximo de zero for o valor em dBm, mais forte é o sinal.

Exemplo:
- Biblioteca: -45 dBm — Excelente
- Sala: -58 dBm — Bom
- Cantina: -68 dBm — Regular

Os valores acima são apenas exemplos; os valores reais devem ser obtidos durante o teste.

## 9. Evidências
Adicionar screenshots mostrando:
1. Tela inicial.
2. Pedido/concessão das permissões.
3. Lista de redes reais.
4. Pesquisa com filtro por SSID.
5. Resultados em diferentes pontos do campus.
