<template>
  <div class="border container-lg p-5 mb-5">
    <Icon name="profile-account-overview" :size="1.3"/>
    <h2 class="h2 mt-2">Edit details</h2>
<!--    <p class="mt-2">Feel free to edit any of your details below.</p>-->
    <div class="mt-4">
      <div v-for="item in data" :key="item" style="max-width: 1000px;" class="card">
        <div class="card-body ">
          <p>
            <strong class="d-block text-gray-dark">{{item.user.firstName}} {{item.user.lastName}}</strong>
            {{item.message}}
          </p>
          <p class="card-text">
            <small>Created:
              <b>{{ item.genDate }}</b>
            </small>
          </p>
          <div>
            <button @click="sendResponse(item.id, 'SATISFY')" class="btn btn-success mr-3" type="btn button">Satisfy</button>
            <button @click="sendResponse(item.id, 'REJECTED')" class="btn btn-warning" type="btn button">Reject</button>
          </div>
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
        admin: this.userId,
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