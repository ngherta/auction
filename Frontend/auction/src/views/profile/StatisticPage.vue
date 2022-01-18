<template>
  <top-categories :data="this.topCategoriesData"/>
</template>

<script>
import StatisticService from "../../services/statistic.service";
import TopCategories from "./admin/TopCategories";

export default {
  name: "StatisticPage",
  components: {
    TopCategories,
  },
  data() {
    return {
      topCategoriesData: null
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
  }
}
</script>

<style scoped>

</style>