const regexp = /(\w+?)(:||-|\\s)(\w+)/gm;

class DateConverter {
    convert(dateStr) {
        console.log(dateStr);
        const matches = dateStr.match(regexp);
        console.log(matches);
        let day = matches[0];
        let month = matches[1];
        console.log(month);
        if (month[0] == '0') {
            month = month[1] - 1;
        }
        console.log(month);
        let year = matches[2];
        let hour = matches[3];
        let minute = matches[4];
        return new Date(year, month, day, hour, minute, 0, 0)
    }
}

export default new DateConverter();
