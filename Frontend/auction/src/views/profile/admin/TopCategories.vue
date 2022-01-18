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

export default {
  name: "Chart",
  props: [
    'data'
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
      barChart: null,
    }
  },
  created() {
    let colors = [];

    for (let i = 0; i < this.labels.length; i++) {
      colors.push('#' + (Math.random() * 0xFFFFFF << 0).toString(16));
    }
    for (let i = 0; i < this.data.length; i++) {
      this.labels.push(this.data[i].name);
      this.values.push(this.data[i].count);
    }

    this.barChart = {
      id: 'bar',
      type: 'bar',
      data: {
        labels: this.labels,
        datasets: [
          {
            backgroundColor: colors,
            data: this.values
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
}
</script>

<style scoped>

</style>