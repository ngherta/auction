<template>
  <div class="container">
    <div>
      <span>Controllers: {{ this.status.controller }}</span>
      <form action="">
        <span>status.controller: {{ this.status.controller }} <br></span>
        <span>TEST: {{ this.selectedStatus.controller }}</span>
        <select class="custom-select"
                v-model="this.selectedStatus.controller">
          <option v-for="logLevel in logLevels"
                  :key="logLevel"
                  :value="logLevel"
                  :selected="logLevel == this.selectedStatus.controller">
            {{ logLevel }}
          </option>
        </select>

        <button type="button"
                @click="update('controller', this.selectedStatus.controller)"
                class="btn btn-primary"
                v-if="this.status.controller != this.test">
          Update
        </button>
        <button type="button"
                class="btn btn-light"
                v-if="this.status.controller == this.test"
                disabled>
          Update
        </button>

      </form>
    </div>
  </div>
</template>

<script>
import LoggingService from '../../../services/logging.service'

export default {
  name: "Logging",
  data() {
    return {
      logLevels: ['OFF', 'ERROR', 'WARN', 'INFO', 'DEBUG', 'TRACE'],
      loggersName: {
        'controller': 'com.auction.web.controller',
        'service': 'com.auction.service',
        'listener': 'com.auction.listener',
        'request': 'com.auction.config.logging.RequestResponseLoggingFilter'
      },
      status: {
        'controller': '',
        'service': '',
        'listener': '',
        'request': ''
      },
      selectedStatus: {
        'controller': '',
        'service': '',
        'listener': '',
        'request': ''
      },
    }
  },
  methods: {
    getStatus() {
      LoggingService.getLogLevel().then(
          (response) => {
            console.log(response.data);
            this.status.controller = response.data.loggers['com.auction.web.controller']['configuredLevel'];
            this.status.service = response.data.loggers['com.auction.service']['configuredLevel'];
            this.status.listener = response.data.loggers['com.auction.listener']['configuredLevel'];
            this.status.request = response.data.loggers['com.auction.config.logging.RequestResponseLoggingFilter']['configuredLevel'];

            this.selectedStatus = this.status;
          }
      )
    },
    update(loggerName, logLevel) {
      this.message = "";
      this.successful = false;
      this.loading = true;
      console.log(loggerName.toString() + " : " + logLevel);

      LoggingService.changeLogLevel(this.loggersName[loggerName], logLevel).then(
          (response) => {
            console.log(response);
            this.status[loggerName] = this.selectedStatus[loggerName];
          },
          (error) => {
            this.$notify({
              text: error.response.data.errorMessage,
              type: 'error'
            });
          }
      )
      console.log(loggerName + " : " + logLevel);
    }
  },
  mounted() {
    this.getStatus();
  }
}
</script>

<style scoped>

</style>