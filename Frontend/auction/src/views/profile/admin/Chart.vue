<template>
  <div style="height:600px;width: 600px;">
    <vue3-chart-js
        :id="doughnutChart.id"
        :type="doughnutChart.type"
        :data="doughnutChart.data"
        @before-render="beforeRenderLogic"
    ></vue3-chart-js>
  </div>
</template>

<script>
import Vue3ChartJs from '@j-t-mcc/vue3-chartjs'

export default {
  name: "Chart",
  components: {
    Vue3ChartJs,
  },
  props: ['data'],
  setup() {
    const doughnutChart = {
      id: 'doughnut',
      type: 'doughnut',
      data: {
        labels: ['VueJs', 'EmberJs', 'ReactJs', 'AngularJs'],
        datasets: [
          {
            backgroundColor: [
              '#41B883',
              '#E46651',
              '#00D8FF',
              '#DD1B16'
            ],
            data: [40, 20, 80, 10]
          }
        ]
      }
    }

    const beforeRenderLogic = (event) => {
      //...
      //if(a === b) {
      //  event.preventDefault()
      //}
    }

    return {
      doughnutChart,
      beforeRenderLogic
    }
  },
}
</script>

<style scoped>

</style>