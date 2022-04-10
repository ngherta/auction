<template>
  <h2 class="text-center mb-4">Company turnover</h2>
  <div style="width: 600px;" class="mb-5">
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
  name: "MoneyStatistic",
  props: ['data'],
  components: {
    Vue3ChartJs,
  },
  data() {
    return {
      type: 'bar',
      values: [],
      labels: [],
      barChart: null,
    }
  },
  created() {
    let colors = [];

    for (let i = 0; i < this.data.length; i++) {
      this.labels.push(this.data[i].month);
      this.values.push(this.data[i].amount);
    }

    for (let i = 0; i < this.labels.length; i++) {
      colors.push('#' + (Math.random() * 0xFFFFFF << 0).toString(16));
    }
    console.log(this.values);
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
  methods: {

  },
  setup() {
    const beforeRenderLogic = () => {
    }

    return {
      beforeRenderLogic
    }
  },
}
</script>

<style scoped>

</style>