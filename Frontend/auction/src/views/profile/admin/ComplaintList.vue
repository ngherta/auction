<template>
  <div class="border container-lg p-5 mb-5">
    <Icon name="profile-account-overview" :size="1.3"/>
    <h2 class="h2 mt-2">Complaints</h2>
<!--    <p class="mt-2">Feel free to edit any of your details below.</p>-->
    <div class="mt-4">

      <div v-for="item in data" :key="item" style="max-width: 1000px;" class="card mb-4">
        <div class="card-body ">
          <p>
            <strong class="text-gray-dark">From:</strong> {{item.user.firstName}} {{item.user.lastName}}
          </p>
          <p>
            <strong>Message: </strong>
            {{item.message}}
          </p>
          <p>
            <strong>Status: </strong>
            {{item.status}}
          </p>
          <p class="card-text">
            <small>Created:
              <b>{{ item.genDate }}</b>
            </small>
          </p>
          <div v-if="item.status == 'WAITING'">
            <button @click="sendResponse(item, 'SATISFIED')" class="btn btn-success mr-3" type="btn button">Satisfy</button>
            <button @click="sendResponse(item, 'REJECTED')" class="btn btn-warning" type="btn button">Reject</button>
          </div>
        </div>
      </div>
      <h4 v-if="data.length == 0"
          class="alert text-center alert-dark mt-5" role="alert">
        List is empty :(
      </h4>
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
          }
      )
    },
    sendResponse(item, status) {
      ComplainService.sendResponse({
        admin: this.userId,
        complaintId: item.id,
        status: status
      }).then(
          (response) => {
            item.status = response.data.complaintDto.status;
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