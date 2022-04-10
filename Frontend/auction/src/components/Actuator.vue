<template>
  <div class="spring-info-container">
    <h4 class="h4">Info</h4>
    <ul>
      <li><strong>Application name: </strong> {{ actuatorInfo.name }}</li>
      <li><strong>Description:</strong> {{ actuatorInfo.description }}</li>
      <li><strong>Application version: </strong>{{ actuatorInfo.version }}</li>
      <li><strong>Java version:</strong> {{ actuatorInfo.javaVersion }}</li>
    </ul>
    <h4 class="h4">Health</h4>
    <ul>
      <li><strong>The state of application:</strong> {{ actuatorHealth.status }}</li>
      <li><strong>Database:</strong> {{ actuatorHealth.database }}</li>
      <li><strong>Total diskspace: </strong>{{ actuatorHealth.totalDiskSpace }}</li>
      <li><strong>Free diskspace: </strong>{{ actuatorHealth.freeDiskSpace }}</li>
    </ul>
    <h4 class="h4">Metrics</h4>
    <ul>
      <li><strong>The number of processors available:</strong> {{ this.NumberOfProcessors }}</li>
      <li><strong>The uptime: </strong>{{ this.UpTime }}</li>
      <li><strong>Memory used: </strong>{{ this.MemoryUsed }}</li>
      <li><strong>Count of http server request: </strong>{{ this.HttpRequest }}</li>
      <li><strong>Total time of http server requests: </strong> {{ this.HttpRequestTotal }}</li>
      <li><strong>Maximum of http server requests: </strong>{{ this.HttpRequestMax }}</li>
    </ul>

  </div>
</template>

<script>
import ActuatorService from "../services/actuator.service";

export default {
  name: "Actuator",
  data() {
    return {
      MemoryUsed: "",
      NumberOfProcessors: "",
      HttpRequest: "",
      HttpRequestMax: "",
      HttpRequestTotal: "",
      UpTime: "",
      actuatorHealth : "",
      actuatorInfo : "",
    }
  },
  methods: {
    getActuatorData() {
      ActuatorService.getActuatorMetrics("jvm.buffer.memory.used").then(
          (response) => {
            this.MemoryUsed = response.data.measurements[0].value;
          },
          (error) => {
            this.$notify({
              text: error.response.data.errorMessage,
              type: 'error'
            });
          })

      ActuatorService.getActuatorMetrics("system.cpu.count").then(
          (response) => {
            this.NumberOfProcessors = response.data.measurements[0].value;
          },
          (error) => {
            this.$notify({
              text: error.response.data.errorMessage,
              type: 'error'
            });
          })

      ActuatorService.getActuatorMetrics("http.server.requests").then(
          (response) => {
            this.HttpRequest = response.data.measurements[0].value;
            this.HttpRequestTotal = response.data.measurements[1].value;
            this.HttpRequestMax = response.data.measurements[2].value;
          },
          (error) => {
            this.$notify({
              text: error.response.data.errorMessage,
              type: 'error'
            });
          })

      ActuatorService.getActuatorMetrics("process.uptime").then(
          (response) => {
            this.UpTime = response.data.measurements[0].value;
          },
          (error) => {
            this.content =error
          }
      )
    },

    getActuatorHealth() {
      ActuatorService.getActuatorHealth().then(
          (response) => {
            this.actuatorHealth = {
              status : response.status,
              database : response.data.components.db.details.database,
              totalDiskSpace : response.data.components.diskSpace.details.total,
              freeDiskSpace : response.data.components.diskSpace.details.free

            };
          },
          (error) => {
            this.content = error;
          }
      )
    },

    getActuatorInfo() {
      ActuatorService.getActuatorInfo().then(
          (response) => {
            this.actuatorInfo = {
              name : response.data.app.name,
              description : response.data.app.description,
              version : response.data.app.version,
              javaVersion : response.data.app.java.version,
            };
          },
          (error) => {
            this.$notify({
              text: error.response.data.errorMessage,
              type: 'error'
            });
          }
      )
    }
  },
  mounted() {
    this.getActuatorData();
    this.getActuatorInfo();
    this.getActuatorHealth();
  }
}
</script>

<style scoped>

</style>