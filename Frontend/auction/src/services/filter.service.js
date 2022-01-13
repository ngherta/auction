const regexp = /(\w+?)(:|<|>|::)(\w+?),/gm;

class FilterService {
    initFilter(filter, key) {
        let newFilter = '';
        if (filter != '') {
            const matches = filter.match(regexp);
            for (const match of matches) {
                if (key == 'title' || key == 'categories') {
                    if (!match.includes('title:')) {
                        newFilter = newFilter + match;
                    }
                }
            }
        }
        return newFilter;
    }

    prepareFilterForRequest(filter) {
        if (filter.slice(-1) == ',') {
            filter = filter.slice(0, -1);
        }
        return filter;
    }

    getCategories(filter) {
        let categories = [];
        const matches = filter.match(regexp);
        for (const match of matches) {
            if (match.includes('categories::')) {
                categories.push(match
                    .replaceAll('categories::', '')
                    .replaceAll(',', ''));
            }
        }
        return categories;
    }

    removeSubCategory(filter, subCategory) {
        let newFilter = '';
        const matches = filter.match(regexp);
        for (const match of matches) {
            if (!match.includes('categories::' + subCategory)) {
                newFilter = newFilter + match;
            }
        }
        return newFilter;
    }

    equals(filter, key, value,) {
        filter = this.initFilter(filter, key);
        return filter + key + ':' + value + ',';
    }

    in(filter, key, value) {
        console.log("Add");
        console.log(filter);
        filter = this.initFilter(filter, key);
        return filter + key + '::' + value + ',';
    }

    less(filter, key, value) {
        filter = this.initFilter(filter, key);
        return filter + key + '<' + value + ',';
    }

    greater(filter, key, value) {
        filter = this.initFilter(filter, key);
        return filter + key + '>' + value + ',';
    }
}

export default new FilterService();
