
const app = {
    users: [],
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
    renderUsers: function (users) {
        let data = '';

        if (this.users.length > 0) {
            for (i = 0; i < this.users.length; i++) {
                data += `<tr id="user${this.users[i].userId}">`;
                data += `<td>${this.users[i].userId}</td>`;
                data += `<td>${this.users[i].userName}</td>`;
                data += `<td>${this.users[i].ini}</td>`;
                data += `<td>${this.users[i].roles.reduce((el, acc) => acc + ', ' + el)}</td>`;
                data += `<td>${this.users[i].cpr}</td>`;
                data += `<td><button type="button" class="editbtn btn btn-primary" data-id="${this.users[i].userId}" aria-label="Edit">&#9998;</button></td>`;
                data += `<td><button type="button" onclick="app.closebtn(this)" class="closebtn btn btn-danger" 
                            data-name="${this.users[i].userName}"
                            data-id="${this.users[i].userId}" aria-label="Close">&times;</button></td>`;
                data += `</tr>`;
            }
        }
        console.log(this.users.length)
        this.Count(this.users.length);
        this.el.html(data);
    },
    FetchAll: function () {
        $.get('/api/users').done((data) => {
            this.users = data;
            this.renderUsers(data);
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
                this.FetchAll();
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
    }
}

$(document).ready(() => {

    Particles.init({ selector: '.background', maxParticles: 250, connectParticles: true, minDistance: 150, speed: 0.35, color: '#CECECE' });
    app.el = $('#user');
    app.FetchAll();

})


