package com.github.stoton.timetablebackend.parser.optivum.strategy;

import com.github.stoton.timetablebackend.domain.Lesson;
import org.jsoup.nodes.Document;

import java.util.List;

public  class OptivumTimetableStudentStrategy implements OptivumTimetableStrategy {

    @Override
    public List<Lesson> parseAllLessonsFromHtml(Document document) {
        String html = "<html><head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "<meta http-equiv=\"Content-Language\" content=\"pl\">\n" +
                "<meta name=\"description\" content=\". /nPlan lekcji oddziału 3Zm/n utworzony za pomocą programu Plan lekcji Optivum firmy VULCAN\">\n" +
                "<title>Plan lekcji oddziału - 3Zm</title>\n" +
                "<link rel=\"stylesheet\" href=\"../css/plan.css\" type=\"text/css\">\n" +
                "<script language=\"JavaScript1.2\" type=\"text/javascript\" src=\"../scripts/plan.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"tabtytul\">\n" +
                "<tr>\n" +
                "<td class=\"tytul\">\n" +
                "<img src=\"../images/pusty.gif\" height=\"80\" width=\"1\">\n" +
                "<span class=\"tytulnapis\">3Zm</span></td></tr></table>\n" +
                "<div align=\"center\">\n" +
                "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\">\n" +
                "<tr><td colspan=\"2\">\n" +
                "<table border=\"1\" cellspacing=\"0\" cellpadding=\"4\" class=\"tabela\">\n" +
                "<tr>\n" +
                "<th>Nr</th>\n" +
                "<th>Godz</th>\n" +
                "<th>Poniedziałek</th>\n" +
                "<th>Wtorek</th>\n" +
                "<th>Środa</th>\n" +
                "<th>Czwartek</th>\n" +
                "<th>Piątek</th>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"nr\">1</td>\n" +
                "<td class=\"g\"> 8:00- 8:45</td>\n" +
                "<td class=\"l\"><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-1/3</span> <a href=\"n24.html\" class=\"n\">Km</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-2/3</span> <a href=\"n53.html\" class=\"n\">ZS</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-3/3</span> <a href=\"n19.html\" class=\"n\">MK</a> <span class=\"s\">@</span></span></td>\n" +
                "<td class=\"l\"><span class=\"p\">pod.i.pr.dzg</span> <a href=\"n66.html\" class=\"n\">WA</a> <a href=\"s25.html\" class=\"s\">309</a></td>\n" +
                "<td class=\"l\"><span class=\"p\">pod.i.pr.dzg</span> <a href=\"n66.html\" class=\"n\">WA</a> <a href=\"s25.html\" class=\"s\">309</a></td>\n" +
                "<td class=\"l\"><span class=\"p\">matematyka</span> <a href=\"n29.html\" class=\"n\">KJ</a> <a href=\"s8.html\" class=\"s\">204</a></td>\n" +
                "<td class=\"l\"><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-1/3</span> <a href=\"n24.html\" class=\"n\">Km</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-2/3</span> <a href=\"n53.html\" class=\"n\">ZS</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-3/3</span> <a href=\"n19.html\" class=\"n\">MK</a> <span class=\"s\">@</span></span></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"nr\">2</td>\n" +
                "<td class=\"g\"> 8:50- 9:35</td>\n" +
                "<td class=\"l\"><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-1/3</span> <a href=\"n24.html\" class=\"n\">Km</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-2/3</span> <a href=\"n53.html\" class=\"n\">ZS</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-3/3</span> <a href=\"n19.html\" class=\"n\">MK</a> <span class=\"s\">@</span></span></td>\n" +
                "<td class=\"l\"><span class=\"p\">j.polski</span> <a href=\"n62.html\" class=\"n\">US</a> <a href=\"s22.html\" class=\"s\">303</a></td>\n" +
                "<td class=\"l\"><span style=\"font-size:85%\"><span class=\"p\">j.angielski-1/2</span> <a href=\"n18.html\" class=\"n\">Je</a> <a href=\"s5.html\" class=\"s\">114</a></span><br><span style=\"font-size:85%\"><span class=\"p\">j.angielski-2/2</span> <a href=\"n11.html\" class=\"n\">DS</a> <a href=\"s32.html\" class=\"s\">112(2)</a></span></td>\n" +
                "<td class=\"l\"><span class=\"p\">religia</span> <a href=\"n32.html\" class=\"n\">PK</a> <a href=\"s10.html\" class=\"s\">206</a></td>\n" +
                "<td class=\"l\"><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-1/3</span> <a href=\"n24.html\" class=\"n\">Km</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-2/3</span> <a href=\"n53.html\" class=\"n\">ZS</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-3/3</span> <a href=\"n19.html\" class=\"n\">MK</a> <span class=\"s\">@</span></span></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"nr\">3</td>\n" +
                "<td class=\"g\"> 9:40-10:25</td>\n" +
                "<td class=\"l\"><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-1/3</span> <a href=\"n24.html\" class=\"n\">Km</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-2/3</span> <a href=\"n53.html\" class=\"n\">ZS</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-3/3</span> <a href=\"n19.html\" class=\"n\">MK</a> <span class=\"s\">@</span></span></td>\n" +
                "<td class=\"l\"><span class=\"p\">wf</span>-1/2 <span class=\"p\">#W6</span> <span class=\"s\">@</span><br><span style=\"font-size:85%\"><span class=\"p\">wf-2/2</span> <a href=\"n8.html\" class=\"n\">CU</a> <span class=\"s\">@</span></span></td>\n" +
                "<td class=\"l\"><span class=\"p\">wf</span>-1/2 <span class=\"p\">#W6</span> <span class=\"s\">@</span><br><span style=\"font-size:85%\"><span class=\"p\">wf-2/2</span> <a href=\"n8.html\" class=\"n\">CU</a> <span class=\"s\">@</span></span></td>\n" +
                "<td class=\"l\"><span class=\"p\">wos</span> <a href=\"n14.html\" class=\"n\">BD</a> <a href=\"s25.html\" class=\"s\">309</a></td>\n" +
                "<td class=\"l\"><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-1/3</span> <a href=\"n24.html\" class=\"n\">Km</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-2/3</span> <a href=\"n53.html\" class=\"n\">ZS</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-3/3</span> <a href=\"n19.html\" class=\"n\">MK</a> <span class=\"s\">@</span></span></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"nr\">4</td>\n" +
                "<td class=\"g\">10:45-11:30</td>\n" +
                "<td class=\"l\"><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-1/3</span> <a href=\"n24.html\" class=\"n\">Km</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-2/3</span> <a href=\"n53.html\" class=\"n\">ZS</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-3/3</span> <a href=\"n19.html\" class=\"n\">MK</a> <span class=\"s\">@</span></span></td>\n" +
                "<td class=\"l\"><span class=\"p\">wf</span>-1/2 <span class=\"p\">#W6</span> <span class=\"s\">@</span><br><span style=\"font-size:85%\"><span class=\"p\">wf-2/2</span> <a href=\"n8.html\" class=\"n\">CU</a> <span class=\"s\">@</span></span></td>\n" +
                "<td class=\"l\"><span class=\"p\">ob.i.n.masz.</span> <a href=\"n28.html\" class=\"n\">KZ</a> <a href=\"s26.html\" class=\"s\">310</a></td>\n" +
                "<td class=\"l\"><span class=\"p\">ob.i.n.masz.</span> <a href=\"n28.html\" class=\"n\">KZ</a> <a href=\"s26.html\" class=\"s\">310</a></td>\n" +
                "<td class=\"l\"><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-1/3</span> <a href=\"n24.html\" class=\"n\">Km</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-2/3</span> <a href=\"n53.html\" class=\"n\">ZS</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-3/3</span> <a href=\"n19.html\" class=\"n\">MK</a> <span class=\"s\">@</span></span></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"nr\">5</td>\n" +
                "<td class=\"g\">11:35-12:20</td>\n" +
                "<td class=\"l\"><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-1/3</span> <a href=\"n24.html\" class=\"n\">Km</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-2/3</span> <a href=\"n53.html\" class=\"n\">ZS</a> <span class=\"s\">@</span></span><br><span style=\"font-size:85%\"><span class=\"p\">zaj.prak.-3/3</span> <a href=\"n19.html\" class=\"n\">MK</a> <span class=\"s\">@</span></span></td>\n" +
                "<td class=\"l\"><span class=\"p\">religia</span> <a href=\"n32.html\" class=\"n\">PK</a> <a href=\"s10.html\" class=\"s\">206</a></td>\n" +
                "<td class=\"l\"><span style=\"font-size:85%\"><span class=\"p\">z_j.obcy-1/2</span> <a href=\"n11.html\" class=\"n\">DS</a> <a href=\"s32.html\" class=\"s\">112(2)</a></span><br><span style=\"font-size:85%\"><span class=\"p\">z_j.obcy-2/2</span> <a href=\"n18.html\" class=\"n\">Je</a> <a href=\"s5.html\" class=\"s\">114</a></span></td>\n" +
                "<td class=\"l\"><span class=\"p\">pod.i.pr.dzg</span> <a href=\"n66.html\" class=\"n\">WA</a> <a href=\"s24.html\" class=\"s\">308</a></td>\n" +
                "<td class=\"l\">&nbsp;</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"nr\">6</td>\n" +
                "<td class=\"g\">12:25-13:10</td>\n" +
                "<td class=\"l\">&nbsp;</td>\n" +
                "<td class=\"l\"><span class=\"p\">matematyka</span> <a href=\"n29.html\" class=\"n\">KJ</a> <a href=\"s16.html\" class=\"s\">219</a></td>\n" +
                "<td class=\"l\"><span class=\"p\">ob.i nap.poj</span> <a href=\"n28.html\" class=\"n\">KZ</a> <a href=\"s26.html\" class=\"s\">310</a></td>\n" +
                "<td class=\"l\"><span style=\"font-size:85%\"><span class=\"p\">j.angielski-1/2</span> <a href=\"n18.html\" class=\"n\">Je</a> <a href=\"s5.html\" class=\"s\">114</a></span><br><span style=\"font-size:85%\"><span class=\"p\">j.angielski-2/2</span> <a href=\"n11.html\" class=\"n\">DS</a> <a href=\"s32.html\" class=\"s\">112(2)</a></span></td>\n" +
                "<td class=\"l\">&nbsp;</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"nr\">7</td>\n" +
                "<td class=\"g\">13:15-14:00</td>\n" +
                "<td class=\"l\">&nbsp;</td>\n" +
                "<td class=\"l\"><span class=\"p\">ob.i nap.poj</span> <a href=\"n28.html\" class=\"n\">KZ</a> <a href=\"s26.html\" class=\"s\">310</a></td>\n" +
                "<td class=\"l\"><span class=\"p\">ob.i nap.poj</span> <a href=\"n28.html\" class=\"n\">KZ</a> <a href=\"s26.html\" class=\"s\">310</a></td>\n" +
                "<td class=\"l\"><span class=\"p\">zaj.wych</span> <a href=\"n19.html\" class=\"n\">MK</a> <a href=\"s21.html\" class=\"s\">302</a></td>\n" +
                "<td class=\"l\">&nbsp;</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"nr\">8</td>\n" +
                "<td class=\"g\">14:05-14:50</td>\n" +
                "<td class=\"l\">&nbsp;</td>\n" +
                "<td class=\"l\"><span class=\"p\">ob.i.n.masz.</span> <a href=\"n28.html\" class=\"n\">KZ</a> <a href=\"s26.html\" class=\"s\">310</a></td>\n" +
                "<td class=\"l\">&nbsp;</td>\n" +
                "<td class=\"l\">&nbsp;</td>\n" +
                "<td class=\"l\">&nbsp;</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td></tr>\n" +
                "<tr><td align=\"left\"><a href=\"javascript:window.print()\">Drukuj plan</a></td><td class=\"op\" align=\"right\">\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "<tr><td align=\"right\">\n" +
                "wygenerowano 2018-05-12<br>\n" +
                "za pomocą programu\n" +
                "<a href=\"http://www.vulcan.edu.pl/dla_szkol/optivum/plan_lekcji/Strony/wstep.aspx\" target=\"_blank\">Plan lekcji Optivum</a><br>\n" +
                "firmy <a href=\"http://www.vulcan.edu.pl/\" target=\"_blank\">VULCAN</a></td>\n" +
                "<td><img border=\"0\" src=\"../images/plan_logo.gif\" style=\"margin-left:10\" alt=\"logo programu Plan lekcji Optivum\" width=\"40\" height=\"40\"></td>\n" +
                "</tr></table>\n" +
                "<tr><td>\n" +
                "<script type=\"text/javascript\" src=\"../scripts/powrot.js\"></script>\n" +
                "</td></tr>\n" +
                "</td></tr></table></div>\n" +
                "</body>\n" +
                "</html>\n";

        return null;
    }
}
