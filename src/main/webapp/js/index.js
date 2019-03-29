const base = "";

new Vue({
  el: '#app',
  data: {
    columns: ['User ID', 'CPR', 'Username', 'Initials', 'Roles', 'Actions '],
    people: [],
    input: {
      lname: "WADE",
      fname: "Johnson",
      age: 38,
      job: "Comedian",
      address: "Roma/Italia"
    },
    editInput: {
      lname: "",
      fname: "",
      age: 0,
      job: "",
      address: ""
    }
  },
  mounted: function () {
     this.getAndSetAll()
  },
  methods: {
    //function to add data to table
    getAndSetAll: function(){
      $.get(base + "/api/users/").done(e => {
        this.people = e.data;
      });
    },
    add: function() {
      /*this.people.push({
        lname: this.input.lname,
        fname: this.input.fname,
        age: this.input.age,
        job: this.input.job,
        address: this.input.address
      });

      for (var key in this.input) {
        this.input[key] = '';
      }
      this.people.sort(ordonner);
      this.$refs.lname.focus();*/
    },
    //function to handle data edition
    edit: function(index) {
      this.editInput = this.people[index];
      console.log(this.editInput);
      this.people.splice(index, 1);
    },
    //function to send data to bin
    delete: function(index) {
      this.bin.push(this.people[index]);
      this.people.splice(index, 1);
      this.bin.sort(ordonner);
    },
    //function to restore data
    restore: function(index) {
      this.people.push(this.bin[index]);
      this.bin.splice(index, 1);
      this.bin.sort(ordonner);
    },
    //function to update data
    update: function(){
      // this.people.push(this.editInput);
       this.people.push({
        lname: this.editInput.lname,
        fname: this.editInput.fname,
        age: this.editInput.age,
        job: this.editInput.job,
        address: this.editInput.address
      });
       for (var key in this.editInput) {
        this.editInput[key] = '';
      }
      this.people.sort(ordonner);
    },
    //function to defintely delete data 
    deplete: function(index) {
      // console.log(this.bin[index]);
      this.bin.splice(index, 1);
    }
  }
});

//function to sort table data alphabetically
var ordonner = function(a, b) {
  return (a.lname > b.lname);
};

$(function() {
  //initialize modal box with jquery
  $('.modal').modal();
});