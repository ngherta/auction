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
import AuctionService from "@/services/auction.service";

export default {
  name: "AuctionTable",
  components: {TableLite},
  methods: {
    deleteUserById(id) {
      AuctionService.delete(id).then(
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
          label: "Title",
          field: "title",
          width: "15%",
          sortable: true,
        },
        {
          label: "Status",
          field: "statusType",
          width: "7%",
          sortable: true,
          display: function (row) {
            let statusClass;
            if (row.statusType == 'ACTIVE') {
              statusClass = 'bg-success';
            }
            else if(row.statusType == 'EXPECTATION') {
              statusClass = 'bg-info text-dark';
            }
            else if(row.statusType == 'FINISHED') {
              statusClass = 'bg-warning text-dark';
            }
            else {
              statusClass = 'bg-danger';
            }
            return ('<span class="badge ' + statusClass + '">'+ row.statusType + '</span>')
          }
        },
        {
          label: "Start price",
          field: "startPrice",
          width: "7%",
          display: function (row) {
            return (row.startPrice + ' $')
          }
        },
        {
          label: "Finish price",
          field: "finishPrice",
          width: "7%",
          display: function (row) {
            return (row.finishPrice ? row.finishPrice + ' $' : 'No finishPrice')
          }
        },
        {
          label: "Start date",
          field: "startDate",
          width: "10%",
          sortable: true,
        },
        {
          label: "Finish date",
          field: "finishDate",
          width: "10%"
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
      AuctionService.getAll()
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
