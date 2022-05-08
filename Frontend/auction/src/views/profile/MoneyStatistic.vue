<template>
  <h2 class="text-center mb-4">Company turnover</h2>
  <div style="width: 600px;" class="mb-5">
    <vue3-chart-js
        :id="id"
        :type="type"
        :data="this.barChartX.data"
    ></vue3-chart-js>
  </div>
</template>

<script>
import Vue3ChartJs from '@j-t-mcc/vue3-chartjs'
import authHeader from "@/services/auth-header";
import properties from "@/properties";
import axios from 'axios';

export default {
  name: "MoneyStatistic",
  components: {
    Vue3ChartJs,
  },
  data() {
    return {
      type: 'bar',
      barChartX: {
        id: 'bar',
        type: 'bar',
        data: {
          labels: [],
          datasets: [
            {
              backgroundColor: [],
              data: []
            }
          ]
        }
      }
    }
  },
  methods: {
    beforeX() {
      axios.post(properties.API_URL + '/api/statistic', {
        types: ['MONEY']
      }, {
        headers:
            authHeader()
      }).then(
          (response) => {
            console.log(response);
            const moneyData = response.data.commissionPerMouths;
            let colors = [];
            let labels = [];
            let values = [];

            for (let i = 0; i < moneyData.length; i++) {
              labels.push(moneyData[i].month);
              values.push(moneyData[i].amount);
            }

            for (let i = 0; i < labels.length; i++) {
              colors.push('#' + (Math.random() * 0xFFFFFF << 0).toString(16));
            }
            this.barChartX = {
              id: 'bar',
              type: 'bar',
              data: {
                labels: labels,
                datasets: [
                  {
                    backgroundColor: colors,
                    data: values
                  }
                ]
              }
            }
          })
    },
  },
  mounted() {
    this.beforeX();
  },

  setup: function () {
    let barChart = {
      id: 'bar',
      type: 'bar',
      data: {
        labels: [],
        datasets: [
          {
            backgroundColor: [],
            data: []
          }
        ]
      }
    };

    const beforeRenderLogic = () => {
      new Promise((resolve, reject) => {
        axios.post(properties.API_URL + '/api/statistic', {
          types: ['MONEY']
        }, {
          headers:
              authHeader()
        }).then(
            (response) => {
              console.log(response);
              const moneyData = response.data.commissionPerMouths;
              let colors = [];
              let labels = [];
              let values = [];

              for (let i = 0; i < moneyData.length; i++) {
                labels.push(moneyData[i].month);
                values.push(moneyData[i].amount);
              }

              for (let i = 0; i < labels.length; i++) {
                colors.push('#' + (Math.random() * 0xFFFFFF << 0).toString(16));
              }
              barChart = {
                id: 'bar',
                type: 'bar',
                data: {
                  labels: labels,
                  datasets: [
                    {
                      backgroundColor: colors,
                      data: values
                    }
                  ]
                }
              }
              resolve(barChart);
            },
            () => {
              reject(barChart);
            }
        );
      })
    }
    return {
      beforeRenderLogic,
      barChart,
    }
  },
}
</script>

<style scoped>

</style>