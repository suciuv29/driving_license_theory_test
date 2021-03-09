package Model;

import java.util.List;

/**
 * clasa intrebare
 */
public class Intrebare {
    String intrebare;
    List<String> raspunsuri;
    List<String> raspunsuriCorecte;

    /**
     * Constructor cu parametrii
     * @param intrebare
     * @param raspunsuri
     * @param raspunsuriCorecte
     */
    public Intrebare(String intrebare ,List<String> raspunsuri, List<String> raspunsuriCorecte)
    {
        this.intrebare = intrebare;
        this.raspunsuri = raspunsuri;
        this.raspunsuriCorecte = raspunsuriCorecte;

    }

    /**
     * Getters
     * @return
     */
    public String getIntrebare()
    {
        return this.intrebare;
    }
    public List<String> getRaspunsuri()
    {
        return this.raspunsuri;
    }
    public List<String> getRaspunsuriCorecte()
    {
        return this.raspunsuriCorecte;
    }
}
