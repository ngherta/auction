<template>
  <div class="container">
    <table-lite
        :is-loading="table.isLoading"
        :columns="table.columns"
        :rows="table.rows"
        :total="table.totalRecordCount"
        :sortable="table.sortable"
        @do-search="doSearch"
        @is-finished="tableLoadingFinish"
    />
    <div>

    </div>
  </div>
</template>
<script>
import TableLite from 'vue3-table-lite'
import {reactive} from "vue";
import UserService from "@/services/user.service";

export default {
  name: "App",
  components: {TableLite},
  methods: {
    deleteUserById(id) {
      UserService.delete(id).then(
          () => {
          },
          (error) => {
            this.$notify({
              type: 'error',
              text: error.response.data.errorMessage
            })
          }
      )

    }
  },
  setup() {
// Init Your table settings
    const table = reactive({
      isLoading: false,
      columns: [
        {
          label: "ID",
          field: "id",
          width: "3%",
          sortable: true,
          isKey: true,
        },
        {
          label: "Name",
          field: "fullName",
          width: "15%",
          sortable: true,
        },
        {
          label: "Email",
          field: "email",
          width: "15%",
          sortable: true,
        },
        {
          label: "Birthday",
          field: "birthday",
          width: "15%"
        },
        {
          label: "Enabled",
          field: "enabled",
          width: "5%",
          display: function (row) {
            return (row.enabled ? 'Yes' : 'No')
          }
        },
        {
          label: "",
          field: "quick",
          width: "10%",
          display: function (row) {
            return (
                '<button type="button" data-id="' +
                row.id +
                '" class="btn btn-danger">Delete</button>' +
            '<button type="button" data-id="' + 'update' +
            row.id +
            '" class="btn btn-success">Update</button>'
          )
            ;
          },
        },
      ],
      rows: [],
      totalRecordCount: 0,
      sortable: {
        order: "id",
        sort: "asc",
      },
    });

    /**
     * Table search event
     */
    const doSearch = (offset, limit, order, sort) => {
      table.isLoading = true;

// Start use axios to get data from Server
      UserService.getAllUsers()
          .then((response) => {
            console.log(response)
            table.rows = response.data.content;
            table.totalRecordCount = response.data.numberOfElements;
            table.sortable.order = order;
            table.sortable.sort = sort;
          });
// End use axios to get data from Server
    };

    /**
     * Table search finished event
     */
    const tableLoadingFinish = () => {
      table.isLoading = false;
    };

// Get data first
    doSearch(0, 10, 'id', 'asc');

    return {
      table,
      doSearch,
      tableLoadingFinish,
    };
  },
}
</script>
