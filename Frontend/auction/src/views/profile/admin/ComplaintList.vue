<template>
  <div class="container">
    <div v-for="item in data" :key="item" class="card">
      <div class="card-body d-flex">
        <p>
          <strong class="d-block text-gray-dark">{{item.user.firstName}} {{item.user.lastName}}</strong>
          {{item.message}}
        </p>
        <div>
          <button class="btn-primary" type="btn button">Satisfy</button>
          <button class="btn-primary" type="btn button">Block</button>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ComplainService from '@/services/complaint.service';

export default {
  name: "ComplaintList",
  data() {
    return {
      data: [],
      page: 1,
      perPage: 20,
    }
  },
  methods: {
    getComplaintList() {
      ComplainService.getAll(this.page, this.perPage).then(
          (response) => {
            console.log(response.data.content);
            this.data = response.data.content;
          }
      )
    }
  },
  mounted() {
    this.getComplaintList();
  }
}
</script>

<style scoped>

</style>