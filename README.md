# sd_balanceamento

Projeto de Sistemas Distribuídos com Balanceamento de Carga entre Servidores.

O trabalho consiste na implementação de um sistema em 3 camadas, sendo a camada cliente, a do middleware e a de servidores. Sua função é calcular a quantidade de palavras existentes em um texto, independente de seu tamanho.

O cliente, que foi desenvolvido para plataforma android, seleciona um dos textos armazenados na memória do aplicativo e solicita a contagem das palavras. A requisição é enviada para o Middleware que se encarrega de escolher para qual dos servidores enviar o texto para a contagem. A decisão do Middleware é baseada na disponibilidade dos servidores e no seu estado de consumo de CPU. Após decidir o Servidor em melhor estado para contar as palavras, ele envia para este servidor que conta e retorna para o Middleware o resultado, que retorna para o cliente, que por fim exibe o resultado para o Usuário.

Para o monitoramento do estado da CPU dos servidores pelo Middleware, este possui um método POST Http pelo qual os servidores ficam enviando seu estado de CPU a todo instante e que armazena os valores em um objeto específico para cada Servidor. Desta forma, sempre que o serviço for solicitado pelo cliente, o middleware já tem disponível esta informação tornando mais rápida a decisão, deixando mais independente da consistência da rede no momento das requisições.

Do lado do servidor existe o serviço web pelo qual o Middleware faz a requisição e também a aplicação separada para o envio do estado de CPU através do método POST citado acima.
