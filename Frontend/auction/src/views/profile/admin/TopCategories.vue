<template>
  <div style="height:600px;width: 600px;">
    <vue3-chart-js
        :id="id"
        :type="type"
        :data="barChart.data"
        @before-render="beforeRenderLogic"
    ></vue3-chart-js>
  </div>
</template>

<script>
import Vue3ChartJs from '@j-t-mcc/vue3-chartjs'
import StatisticService from "../../../services/statistic.service";

export default {
  name: "Chart",
  components: {
    Vue3ChartJs,
  },
  data() {
    return {
      id: 'bar',
      type: 'bar',
      values: [],
      labels: [],
      data: ""
    }
  },
  setup() {
    const beforeRenderLogic = (event) => {
      // this.getData();
      console.log(event)
    }


    let labels = [];
    let values = [];
    let colors = [];
    StatisticService.getStatisticData().then(
        (response) => {
          for (let i = 0; i < response.data.length; i++) {
            labels.push(response.data[i].name);
            values.push(response.data[i].count);
            colors.push('#'+(Math.random()*0xFFFFFF<<0).toString(16));
          }
          console.log(labels);
        },
        (error) => {
          this.rowData = error.error;
        }
    );

    let barChart = {
      id: 'bar',
      type: 'bar',
      data: {
        // labels: ['VueJs', 'EmberJs', 'ReactJs', 'AngularJs'],
        labels: labels,
        datasets: [
          {
            backgroundColor: colors,
            // data: [40, 20, 80, 10]
            data: values
          }
        ]
      }
    }

    return {
      barChart,
      beforeRenderLogic
    }
  },
  mounted() {
    // this.getData();
  },
  methods: {
    getData() {
      StatisticService.getStatisticData().then(
          (response) => {
            for (let i = 0; i < response.data.length; i++) {
              this.labels.push(response.data[i].name);
              this.values.push(response.data[i].count);
            }
          },
          (error) => {
            this.rowData = error.error;
          }
      );
    }
  }
}
</script>

<style scoped>

</style>