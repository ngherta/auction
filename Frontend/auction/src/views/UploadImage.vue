<template>
  <!-- Button trigger modal -->
  <button ref="modalImageUpload"
          class="d-none"
          type="button"
          data-toggle="modal" 
          data-target="#modalImageUpload">
    Launch demo modal
  </button>

  <!-- Modal -->
  <div class="modal fade"
       id="modalImageUpload"
       tabindex="-1"
       role="dialog"
       aria-labelledby="modalImageUploadTitle"
       aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLongTitle">Upload new image</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <cropper
              ref="cropper"
              class="upload-example-cropper"
              :stencil-size="{
		width: width,
		height: height
	}"
              :stencil-props="{
		handlers: {},
		movable: true,
		resizable: false,
		aspectRatio: 1,
	}"
              :src="image.src"
          />        </div>
        <div class="modal-footer">
          <button type="button" ref="hideModalImage" class="btn btn-secondary" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" data-dismiss="modal" @click="crop">Upload</button>
        </div>
      </div>
    </div>
  </div>
  <div class="upload-example">
    <div class="button-wrapper">
      <form>
        <div class="form-group">
          <label class="d-none" for="image_upload">Choose a file</label>
          <input id="image_upload"
                 type="file"
                 class="form-control-file"
                 ref="file"
                 @change="loadImage($event)"
                 accept="image/*">
        </div>
      </form>
    </div>
  </div>
  <div class="d-flex">
    <div v-if="results">
      <section v-for="result in results" :key="result">
        <img :src="result.secure_url" :height="height" :alt="result.public_id"/>
      </section>
    </div>
  </div>
</template>

<script>
import {Cropper} from 'vue-advanced-cropper'
import axios from "axios";

function getMimeType(file, fallback = null) {
  const byteArray = (new Uint8Array(file)).subarray(0, 4);
  let header = '';
  for (let i = 0; i < byteArray.length; i++) {
    header += byteArray[i].toString(16);
  }
  switch (header) {
    case "89504e47":
      return "image/png";
    case "47494638":
      return "image/gif";
    case "ffd8ffe0":
    case "ffd8ffe1":
    case "ffd8ffe2":
    case "ffd8ffe3":
    case "ffd8ffe8":
      return "image/jpeg";
    default:
      return fallback;
  }
}

export default {
  name: 'UploadImage',
  components: {
    Cropper,
  },
  data() {
    return {
      image: {
        src: null,
        type: null,
      },
      results: [],
      width: 400,
      height: 300,
      formData: null,
      imageFile: '',
      cloudName: "dxn6dcenz",
      preset: "jhjwl2sq",
      tags: "browser-upload",
    };
  },
  methods: {
    crop() {
      const {canvas} = this.$refs.cropper.getResult();
      this.$refs.hideModalImage.click();
      canvas.toBlob((blob) => {
        this.imageFile = blob;
        this.prepareFormData();
        let cloudinaryUploadURL = `https://api.cloudinary.com/v1_1/${this.cloudName}/upload`;
        let requestObj = {
          url: cloudinaryUploadURL,
          method: "POST",
          data: this.formData,
        };
        //show progress bar at beginning of post
        axios(requestObj)
            .then(response => {
              this.results.push(response.data);
              this.imageFile = response.data.secure_url;
              this.$emit('uploadNewImages', response.data.secure_url)
            })
            .catch(error => {
              this.errors.push(error);
            })
      }, 'image/jpeg');
    },
    prepareFormData: function () {
      this.formData = new FormData();
      this.formData.append("upload_preset", this.preset);
      this.formData.append("tags", this.tags);
      this.formData.append("file", this.imageFile);
    },
    reset() {
      this.image = {
        src: null,
        type: null
      }
    },
    loadImage(event) {
      // Reference to the DOM input element
      const {files} = event.target;
      // Ensure that you have a file before attempting to read it
      if (files && files[0]) {
        // 1. Revoke the object URL, to allow the garbage collector to destroy the uploaded before file
        if (this.image.src) {
          URL.revokeObjectURL(this.image.src)
        }
        // 2. Create the blob link to the file to optimize performance:
        const blob = URL.createObjectURL(files[0]);

        // 3. The steps below are designated to determine a file mime type to use it during the
        // getting of a cropped image from the canvas. You can replace it them by the following string,
        // but the type will be derived from the extension and it can lead to an incorrect result:
        //
        // this.image = {
        //    src: blob;
        //    type: files[0].type
        // }

        // Create a new FileReader to read this image binary data
        const reader = new FileReader();
        // Define a callback function to run, when FileReader finishes its job
        reader.onload = (e) => {
          // Note: arrow function used here, so that "this.image" refers to the image of Vue component
          this.image = {
            // Set the image source (it will look like blob:http://example.com/2c5270a5-18b5-406e-a4fb-07427f5e7b94)
            src: blob,
            // Determine the image type to preserve it during the extracting the image from canvas:
            type: getMimeType(e.target.result, files[0].type),
          };
        };
        this.$refs.modalImageUpload.click();

        // Start the reader job - read file as a data url (base64 format)
        reader.readAsArrayBuffer(files[0]);
      }
    }
  },
  unmounted() {
    // Revoke the object URL, to allow the garbage collector to destroy the uploaded before file
    if (this.image.src) {
      URL.revokeObjectURL(this.image.src)
    }
  }
}
</script>

<style scoped>

</style>