<template>
  <div class="border container-lg p-5 mb-5">
    <Icon name="payments" size="3"/>
    <div class="d-flex flex-column">
      <h2 class="h2 mt-2">System information</h2>
      <Actuator v-if="isActiveComponent"/>
    </div>
  </div>
  <div class="container-lg p-5 border">
    <money-statistic/>
<!--    <top-categories :data="this.topCategoriesData"/>-->
<!--    <chart :data="null"></chart>-->
  </div>
</template>

<script>
// import StatisticService from "../../services/statistic.service";
// import TopCategories from "./admin/TopCategories";
import MoneyStatistic from "@/views/profile/MoneyStatistic";
// import Chart from "./admin/Chart";
import Actuator from "../../components/Actuator";

export default {
  name: "StatisticPage",
  components: {
    // TopCategories,
    // Chart,
    Actuator,
    MoneyStatistic
  },
  data() {
    return {
      isActiveComponent: false
    }
  },
  methods: {

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