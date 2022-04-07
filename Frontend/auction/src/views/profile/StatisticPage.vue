<template>
  <Actuator v-if="isActiveComponent"/>
  <top-categories :data="this.topCategoriesData"/>
  <chart :data="null"></chart>
</template>

<script>
import StatisticService from "../../services/statistic.service";
import TopCategories from "./admin/TopCategories";
import Chart from "./admin/Chart";
import Actuator from "../../components/Actuator";

export default {
  name: "StatisticPage",
  components: {
    TopCategories,
    Chart,
    Actuator
  },
  data() {
    return {
      topCategoriesData: null,
      isActiveComponent: false
    }
  },
  methods: {
    getStatisticData() {
      StatisticService.getStatisticData().then(
          (response) => {
            this.topCategoriesData = response.data.categoryCount;
          },
          (error) => {
            this.rowData = error.error;
          }
      );
    }
  },
  created() {
    this.getStatisticData();
  },
  mounted() {
    this.isActiveComponent = true;
  },
  unmounted() {
    this.isActiveComponent = false;
  }
}
</script>

<style scoped>

</style>