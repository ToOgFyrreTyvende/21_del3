const utils = {
    createForm: true,
    users: [],
    roles: [],
    Count: function (data) {
        let name = 'user';
        counter = $('#counter');
        if (data) {
            if (data > 1)
                name = 'users';

            counter.html(data + ' ' + name);
        } else
            counter.html('No ' + name);
    },    
    FetchAllUsers: function () {
        $.get('/api/users').done((data) => {
            this.users = data.sort((a, b) => (a.userId > b.userId) ? 1 : -1);
            this.renderUsers(data);
        })
    },
    FetchAllRoles: function() {
        $.get('/api/users/roles').done((data) => {
            this.roles = data;
            this.renderRoles(data);
        })
    },
    removeWithId: function (id) {
        $.ajax({
            url: `/api/users/${id}`,
            type: 'DELETE',
            dataType: 'text',
            success: () => {
                $(`#user${id}`).remove();
                console.log("Deleted!, fetching now");
                this.FetchAllUsers();
            }
        });
    },
    closebtn: function (el) {
        let id = el.dataset.id;
        let name = el.dataset.name;
        let resp = confirm(`Are you sure you want to delete ${name} with id ${id}?`)
        if (resp) {
            this.removeWithId(id);
        }
    },
    setForm: function(status, id, name) {
        id = id || 0;
        name = name || "";
        if(status === "create" && id === 0){
            this.createForm = true;
            document.getElementById("inputForm").reset();
            $("#modalText").text("Create");
            $("#userIdInput").prop('disabled', false);
        }else{
            this.createForm = false;
            this.renderInputFields(id)
            $("#userIdInput").prop('disabled', true);
            $("#modalText").text(`Update ${name}, #${id}`);
            $('#createUserModal').modal();
        }
    },
    createUser: function(){
        let _this = this;
        $.ajax({
            type: "POST",
            url: "/api/users",
            data: JSON.stringify(getFormData($('#inputForm'))),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data){
                $('#createUserModal').modal('toggle');
                document.getElementById("inputForm").reset();
                _this.FetchAllUsers();
            },
            failure: function(errMsg) {
                alert(errMsg);
                console.error(errMsg);
            }
        });
    },
    updateUser: function(){
        let _this = this;
        $("#userIdInput").prop('disabled', false);
        $.ajax({
            type: "PUT",
            url: "/api/users",
            data: JSON.stringify(getFormData($('#inputForm'))),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data){
                $('#createUserModal').modal('toggle');
                document.getElementById("inputForm").reset();
                _this.FetchAllUsers();
            },
            failure: function(errMsg) {
                alert(errMsg);
                console.error(errMsg);
            }
        });
        $("#userIdInput").prop('disabled', true);

    }

}

const app = Object.assign(utils, renders);

$(document).ready(() => {

    Particles.init({ selector: '.background', maxParticles: 250, connectParticles: true, minDistance: 150, speed: 0.35, color: '#CECECE' });
    app.userEl = $('#user');
    app.roleEl = $('#roles');
    app.FetchAllUsers();
    app.FetchAllRoles();
    $('#submitForm').on('click', (event) => {
        event.preventDefault();
        if(app.createForm)
            app.createUser();
        else
            app.updateUser();
    });
})

function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {roles: []};

    $.map(unindexed_array, function(n, i){
        if(n['name'] == "roles"){
            indexed_array[n['name']].push(n['value']);
        }else{
            indexed_array[n['name']] = n['value'];
        }
    });
    return indexed_array;
}


