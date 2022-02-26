<template>
  <div class="container">
    <div v-for="item in data" :key="item" class="card">
      <div class="card-body d-flex">
        <p>
          <strong class="d-block text-gray-dark">{{item.user.firstName}} {{item.user.lastName}}</strong>
          {{item.message}}
        </p>
        <div>
          <button @click="sendResponse(item.id, 'SATISFY')" class="btn btn-success" type="btn button">Satisfy</button>
          <button @click="sendResponse(item.id, 'REJECTED')" class="btn btn-warning" type="btn button">Reject</button>
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
      userId: this.$store.state.auth.user.userDto.id,
      perPage: 20,
    }
  },
  methods: {
    getComplaintList() {
      ComplainService.getAll(this.page, this.perPage).then(
          (response) => {
            this.data = response.data.content;
            console.log(this.data)
          }
      )
    },
    sendResponse(id, status) {
      ComplainService.sendResponse({
        admin : this.userId,
        complaintId: id,
        status: status
      }).then(
          (response) => {
            console.log(response);
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