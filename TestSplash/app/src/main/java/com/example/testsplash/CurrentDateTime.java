package com.example.testsplash;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class CurrentDateTime {
    private LocalTime time_now;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) {
        // 현재 날짜 구하기 (시스템 시계, 시스템 타임존)
        LocalDate Date_now = LocalDate.now();
        // 결과 출력
        System.out.println(Date_now); // 2021-06-17
        // 포맷 정의
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        // 포맷 적용
        String dateFormatedNow = Date_now.format(dateFormatter);
        // 결과 출력
        System.out.println(dateFormatedNow); // 2021/06/17


        // 현재 시간
        LocalTime time_now = LocalTime.now();
        // 현재시간 출력
        System.out.println(time_now); // 06:20:57.008731300
        // 포맷 정의하기
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH시 mm분 ss초");
        // 포맷 적용하기
        String timeFormatedNow = time_now.format(dateFormatter);
        // 포맷 적용된 현재 시간 출력
        System.out.println(dateFormatedNow); // 06시 20분 57초
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalTime get_time() {
        time_now = LocalTime.now();
        return time_now;
    }
}
