<script>
  import {Pie} from 'vue-chartjs';
  import CONFIG from '../../config';
  import axios from 'axios';
  import SockJS from "sockjs-client";
  import Stomp from "webstomp-client";

  export default {
    extends: Pie,
    name: 'connectivity-graphic',
    methods: {
      connect() {
        this.socket = new SockJS(CONFIG.URL_WS);
        this.stompClient = Stomp.over(this.socket);
        this.stompClient.connect(
          {},
          frame => {
            this.connected = true;
            this.stompClient.subscribe("/topic/reponse/connectivity", message => {
              var resp = JSON.parse(message.body);
              console.log("-------------------- ITEM ATUALIZADO:" + resp);
              console.log(resp);
              console.log("--------------------");
              this.renderGraphic();
            });
          },
          error => {
            console.log(error);
            this.connected = false;
          }
        );
      },
      disconnect() {
        if (this.stompClient) {
          this.stompClient.disconnect();
        }
        this.connected = false;
      },
      tickleConnection() {
        this.connected ? this.disconnect() : this.connect();
      },

      renderGraphic() {
        axios.get(CONFIG.URL_API + 'graphics/connectivity')
          .then(response => {
            // var tk = JSON.parse();
            console.log("-------------------- Dispositivos Conctados");
            console.log(response.data);
            console.log("--------------------");
            this.renderChart({
              datasets: [
                {
                  label: ['CP', ''],
                  backgroundColor: ['#4BC0C0', '#ccc'],
                  data: [response.data.on, response.data.off]
                }
              ],
              labels: [
                'Online',
                'Offline'
              ]
            }, {
              responsive: true,
              maintainAspectRatio: false,
              layout: {
                padding: {
                  left: 0,
                  right: 0,
                  top: 0,
                  bottom: 0
                },
                height: 200
              },
              title: {
                display: false,
                text: 'Dispositos Instalados'
              },
              legend: {
                display: true,
                labels: {
                  fontColor: 'rgb(255, 99, 132)'
                }
              },
              animation: false
            });
          })
          .catch(e => {


          });
      },
    },
    mounted() {
      this.connect();
    },
    created() {
      this.renderGraphic();
    },
    data: () => ({
    })
  }
</script>
<style scoped>
  canvas {
    height: 300px;
  }
</style>
