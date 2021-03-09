package Model;

import java.util.List;

/**
 * Clasa chestionar
 */
public class Chestionar {

    int id;
    int nrChestionar;
    int nrIntrebariGresite;
    int nrIntrebariCorecte;
    List<Intrebare> intrebarile;

    /**
     * Constructor chestionar cu parametrii
     * @param id
     * @param nrChestionar
     * @param nrIntrebariGresite
     * @param nrIntrebariCorecte
     * @param intrebarile
     */
    public Chestionar(int id,int nrChestionar, int nrIntrebariGresite,int nrIntrebariCorecte,List<Intrebare>intrebarile)
    {
        this.id = id;
        this.nrChestionar = nrChestionar;
        this.nrIntrebariGresite = nrIntrebariGresite;
        this.nrIntrebariCorecte = nrIntrebariCorecte;
        this.intrebarile = intrebarile;
    }

    /**
     * Getters + Setters
     * @return
     */
    public int getId()
    {
        return this.id;

    }
    public void setId(int value)
    {
        this.id = value;
    }
    public int getNrChestionar()
    {
        return this.nrChestionar;
    }
    public void setNrChestionar(int value)
    {
        this.nrChestionar = value;
    }
    public int getNrIntrebariGresite()
    {
        return this.nrIntrebariGresite;
    }
    public void setNrIntrebariGresite(int value)
    {
        this.nrIntrebariGresite = value;
    }
    public int getNrIntrebariCorecte()
    {
        return this.nrIntrebariCorecte;
    }
    public void setNrIntrebariCorecte(int value)
    {
        this.nrIntrebariCorecte = value;
    }

    public Intrebare searchIntrebari(int index)
    {
       if(index>0 && index<=26)
       {
           return this.intrebarile.get(index - 1);
       }
       return null;
    }
}
