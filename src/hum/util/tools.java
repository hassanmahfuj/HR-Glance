package hum.util;

import java.awt.Image;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class tools {

    public static void setIcon(JLabel label, String name) {
        ImageIcon icon = new ImageIcon(tools.class.getResource("/hum/icons/" + name));
        double w = label.getWidth() - label.getWidth() * .1;
        double h = label.getHeight() - label.getHeight() * .1;
        Image img = icon.getImage().getScaledInstance((int) w, (int) h, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
    }

    public static void setIcon(JLabel label, String name, int w, int h) {
        ImageIcon icon = new ImageIcon(tools.class.getResource("/hum/icons/" + name));
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
    }

    public static int calcLeaveHour(Date date1, Date date2) {
        long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        int h = ((int) diffInDays + 1) * config.OFFICE_HOUR;
        return h;
    }

    public static int calcDays(Date date1, Date date2) {
        long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        int h = (int) diffInDays;
        return h + 1;
    }

    public static int getWeekendCount(Date m) {
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(m.getTime()));
        int month = Integer.parseInt(new SimpleDateFormat("M").format(m.getTime()));

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        int weekendCount = 0;

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            DayOfWeek day = date.getDayOfWeek();
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                weekendCount++;
            }
        }

        return weekendCount;
    }

    public static int getBusinessDays(Date m) {
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(m.getTime()));
        int month = Integer.parseInt(new SimpleDateFormat("M").format(m.getTime()));

        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        int businessDays = 0;
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = yearMonth.atDay(day);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                businessDays++;
            }
        }

        return businessDays;
    }
}
