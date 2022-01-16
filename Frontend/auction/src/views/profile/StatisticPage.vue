<template>
  <top-categories :statisticValues="this.topCategoriesValues"
                  :statisticLabels="this.topCategoriesLabels"/>
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
      topCategoriesLabels: [],
      topCategoriesValues: [],
    }
  },
  methods: {
    getStatisticData() {
      StatisticService.getStatisticData().then(
          (response) => {
            for (let i = 0; i < response.data.length; i++) {
              this.topCategoriesLabels.push(response.data[i].name);
              this.topCategoriesValues.push(response.data[i].count);
              console.log(this.topCategoriesLabels);
              console.log(this.topCategoriesLabels);
            }
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