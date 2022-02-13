<template>
  <div class="border container-lg p-5 mb-5">
    <Icon name="settings"/>
    <h2 class="h2 mt-2">Settings</h2>
    <div class="mt-4" v-if="data != null">
      <div class="card">
        <h5 class="card-header text-center">
          Notifications
        </h5>
        <ul class="list-group list-group-flush">
          <li v-for="item in data.notifications" :key="item"
              class="list-group-item">
            <div class="custom-control custom-switch">
              <input type="checkbox"
                     @click="updateNotification(item.notificationType, item.value); item.value = !item.value"
                     class="custom-control-input"
                     :id="item.notificationType + 'checkbox'"
                     :checked="item.value">
              <label class="custom-control-label" :for="item.notificationType + 'checkbox'">{{
                  item.name
                }}</label> <Tooltip :text="item.description"/>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import Icon from '../../../components/Icon';
import SettingsService from '../../../services/settings.service';
import Tooltip from "@/components/Tooltip";

export default {
  name: "Settings",
  components: {
    Icon,
    Tooltip,
  },
  data() {
    return {
      data: null,
      userId: this.$store.state.auth.user.userDto.id,
    }
  },
  methods: {
    updateNotification(type, value) {
      SettingsService.update(type, this.userId, !value).then(
          () => {

          },
          (error) => {
            this.$notify({
              text: error.response.data.errorMessage,
              type: 'error'
            });
          }
      )
    },
    getData() {
      SettingsService.getSettings(this.userId).then(
          (response) => {
            this.data = response.data;
          }
      )
    }
  },
  created() {
    this.getData();
  }
}
</script>

<style scoped>

</style>