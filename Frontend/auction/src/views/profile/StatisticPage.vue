<template>
  <div class="border container-lg p-5 mb-5">
    <Icon name="payments" size="3"/>
    <div class="d-flex flex-column">
      <h2 class="h2 mt-2">System information</h2>
      <Actuator v-if="isActiveComponent"/>
    </div>
  </div>
  <div class="container-lg p-5 border">
    <money-statistic :data="this.moneyData"/>
    <top-categories :data="this.topCategoriesData"/>
    <chart :data="null"></chart>
  </div>
</template>

<script>
import StatisticService from "../../services/statistic.service";
import TopCategories from "./admin/TopCategories";
import MoneyStatistic from "@/views/profile/MoneyStatistic";
import Chart from "./admin/Chart";
import Actuator from "../../components/Actuator";

export default {
  name: "StatisticPage",
  components: {
    TopCategories,
    Chart,
    Actuator,
    MoneyStatistic
  },
  data() {
    return {
      topCategoriesData: [],
      moneyData: [],
      isActiveComponent: false
    }
  },
  methods: {
    getStatisticData() {
      StatisticService.getStatisticData().then(
          (response) => {
            console.log(response);
            this.topCategoriesData = response.data.categoryCount;
            this.moneyData = response.data.commissionPerMouths;
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