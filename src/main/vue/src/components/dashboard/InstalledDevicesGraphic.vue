<script>
  import {Bar} from 'vue-chartjs';
  import CONFIG from '../../config';
  import axios from 'axios';
  import SockJS from "sockjs-client";
  import Stomp from "webstomp-client";

  export default {
    extends: Bar,
    name: 'installed-devices-graphic',
    methods: {
      connect() {
        this.socket = new SockJS(CONFIG.URL_WS);
        this.stompClient = Stomp.over(this.socket);
        this.stompClient.connect(
          {},
          frame => {
            this.connected = true;
            this.stompClient.subscribe("/topic/reponse/intallations", message => {
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
        axios.get(CONFIG.URL_API + 'graphics/installations')
          .then(response => {
            // var tk = JSON.parse();
            console.log("-------------------- Dispositivos Instalados");
            console.log(response.data);
            console.log("--------------------");
            this.renderChart({
              datasets: [
                {
                  label: ['CP'],
                  backgroundColor: ['#FFB1C1'],
                  data: [response.data.cp]
                },
                {
                  label: ['CS'],
                  backgroundColor: ['#FFCF9F'],
                  data: [response.data.cs]
                },
                {
                  label: ['UC'],
                  backgroundColor: ['#FFE6AA'],
                  data: [response.data.uc]
                }
              ]
            }, {
              responsive: true,
              maintainAspectRatio: false,
              scales: {
                yAxes: [{
                  ticks: {
                    min: 0,
                    max: response.data.totalUc + Math.trunc(response.data.totalUc / 10),
                    stepSize: Math.trunc(response.data.totalUc / 10),
                  }
                }]
              },
              layout: {
                padding: {
                  left: 0,
                  right: 0,
                  top: 0,
                  bottom: 0
                }
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
