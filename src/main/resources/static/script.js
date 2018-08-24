new Vue({
    el: '#app',
    data: {
        nameStr: [],
        companies: [],
    },
    methods: {
        getIndustry: function () {
            this.companies = this.nameStr.split('\n').filter(s => s);
        }
    }

});

Vue.component('company-industry', {
    data: function() {
        return {
            url: 'Loading...',
            industry: 'Loading...'
        }
    },
    created: function () {
        const vm = this;
        axios
            .get('/search?cName=' + vm.company)
            .then(function (response) {
                    vm.industry = response.data.industry;
                    vm.url = response.data.url;
                    console.log(response.data);
                })
            .catch(function(error) {
                vm.industry = '';
                vm.url = 'ERROR';
                console.log(error.response)
            });
    },
    props: ['company'],
    template: '<tr><td><div style="white-space: nowrap;overflow: hidden;max-width: 500px;text-overflow: ellipsis;"><a v-bind:href="url">{{ url }}</a></div></td><td>{{ industry }}</td></tr>'
})