double totalHours = 0.0;
double grossPay = 0.0;

for (AttendanceLog log : logs) {
    long hours = log.getWorkedHours(); // ← number of hours for this day
    double dailyPay = hours * emp.getHourlyRate(); // ← daily pay
    grossPay += dailyPay; // ← accumulate gross pay
    totalHours += hours;
}
