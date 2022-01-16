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
  props: [
    'statisticLabels', 'statisticValues'
  ],
  components: {
    Vue3ChartJs,
  },
  data() {
    return {
      id: 'bar',
      type: 'bar',
      values: [],
      labels: [],
      data: "",
      barChart: null,
    }
  },
  created() {
    let colors = [];
    console.log(this.statisticLabels);
    console.log(this.statisticValues);

    for (let i = 0; i < this.statisticLabels.length; i++) {
      colors.push('#' + (Math.random() * 0xFFFFFF << 0).toString(16));
    }

    this.barChart = {
      id: 'bar',
      type: 'bar',
      data: {
        labels: this.statisticLabels,
        datasets: [
          {
            backgroundColor: colors,
            data: this.statisticValues
          }
        ]
      }
    }
  },
  setup() {
    const beforeRenderLogic = (event) => {
      console.log(event);
    }

    return {
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