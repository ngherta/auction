<template>
  <div class="container mt-5 mb-5">
    <div class="border p-5">
      <h1 class="h1 mb-3">Add new image to home page</h1>
      <Form @submit="handleUpdate" :validation-schema="schema">
        <div v-if="!successful" class="row align-items-center">
          <div class="col">
            <div class="mb-3">
              <label for="link">LINK:</label>
              <Field name="link" id="link" type="text" class="form-control"/>
              <ErrorMessage name="link" class="error-feedback"/>
            </div>
            <div class="mb-3">
              <label for="sequence">SEQUENCE:</label>
              <Field name="sequence" id="sequence" type="text" class="form-control"/>
              <ErrorMessage name="sequence" class="error-feedback"/>
            </div>

          </div>
          <div class="col">
            <UploadFiles @uploadNewImages="uploadNewImages($event)"/>
          </div>
        </div>
        <div class="row mt-5">
          <button class="btn btn-primary btn-block" :disabled="loading">
              <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"
              ></span>
            CREATE
          </button>
        </div>
        <div class="row mt-3">
          <button type="button"
                  class="btn btn-info btn-block"
                  data-toggle="modal"
                  data-target="#descriptionModalLong">
            PREVIEW
          </button>

          <!-- Modal -->
          <div class="modal fade" id="descriptionModalLong" tabindex="-1" role="dialog"
               aria-labelledby="descriptionModalLongTitle" aria-hidden="true">
            <div class="modal-dialog" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close close-custom" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                  <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                    <div class="carousel-inner">
                      <div v-for="(image, index) of this.sortImages()"
                           :key="image"
                           class="carousel-item "
                           v-bind:class="{active : index == 0}">
                        <router-link :to="image.url">
                          <img class="d-block w-100"
                               v-bind:src="image.imageLink" :alt="index + 'slide'">
                        </router-link>
                      </div>
                    </div>
                    <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
                      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                      <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
                      <span class="carousel-control-next-icon" aria-hidden="true"></span>
                      <span class="sr-only">Next</span>
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </Form>
    </div>
  </div>
</template>

<script>
import UploadFiles from '../../UploadFiles';
import ImageLinkService from '../../../services/imageLink.service';
import {ErrorMessage, Field, Form} from "vee-validate";
import * as yup from "yup";


export default {
  name: "ChangeHomeImagePage",
  components: {
    Form,
    Field,
    ErrorMessage,
    UploadFiles,
  },
  data() {
    const schema = yup.object().shape({
      link: yup
          .string()
          .required("Link is required!")
          .min(5, "Must be at least 3 characters!"),
      sequence: yup
          .string()
          .required("Sequence is required!")
          .min(1, "Must be at least 3 characters!")
          // .notOneOf([''].concat(this.sequenceArr), '"${value}" is not allowed!')
          })

    return {
      images: [],
      sequenceArr: [''],
      loading: false,
      successful: false,
      imageUploaded: null,
      schema
    }
  },
  methods: {
    uploadNewImages(event) {
      this.imageUploaded = event;
    },
    sequenceValidationApi(value) {
      if (this.sequenceArr.contains(value)) {
        return true;
      }
      return false;
    },
    getImageLinks() {
      ImageLinkService.getAllByType('HOME_PAGE').then(
          (response) => {
            this.images = response.data;
            for (let i = 0; i < response.data.length; i++) {
              this.sequenceArr.push(response.data[i].sequence);
            }
          },
          (error) => {
            this.$notify({
              text: error.response.data.errorMessage,
              type: 'error'
            });
          }
      )
    },
    handleUpdate(data) {
      this.loading = true;
      if (this.imageUploaded.length != 1) {
        this.loading = false;
        this.$notify({
          text: 'You should add image!',
          type: 'error'
        });
        return;
      }

      const request = {
        url: data.link,
        imageLink: this.imageUploaded,
        sequence: data.sequence
      }
      console.log(data);
      console.log(request);
      ImageLinkService.create(request, 'HOME_PAGE').then(
          () => {
            this.images.push(request);
            this.successful = true;
          },
          (error) => {
            this.$notify({
              text: error.response.data.errorMessage,
              type: 'error'
            })
          }
      )
      this.loading = false;
    },
    sortImages() {
      return this.images.slice().sort(function (a, b) {
        return (a.sequence > b.sequence) ? 1 : -1;
      });
    }
  },
  mounted() {
    this.getImageLinks();
  }
}
</script>

<style scoped>
@media only screen and (min-width: 576px) {
  .modal-dialog {
    max-width: 500px;
    /*margin: 1.75rem auto;*/
  }
}

@media only screen and (min-width: 992px) {
  .modal-dialog {
    max-width: 600px;
  }
}

@media only screen and (min-width: 1200px) {
  .modal-dialog {
    max-width: 700px;
  }
}

@media only screen and (min-width: 1400px) {
  .modal-dialog {
    max-width: 800px;
  }
}

@media only screen and (min-width: 1600px) {
  .modal-dialog {
    max-width: 900px;
  }
}

.modal-header .close-custom {
  margin: -1rem -1rem -1rem 0;
}
</style>