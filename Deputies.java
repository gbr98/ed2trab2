/**
 * Estrutura que relaciona as informacoes fornecidas no banco de dados ao deputado correspondente
 * 
 */class Deputies {

  private int id;
  private String date;
  private int idDep;
  private String party;
  private String state;
  private String name;
  private double value;

  public Deputies(int id, String date, double value){
    this.id = id;
    this.date = date;
    this.value = value;
  }

  public Deputies(int id, String date, int idDep, String party, String state, String name, double value){
    this.id = id;
    this.date = date;
    this.idDep = idDep;
    this.party = party;
    this.state = state;
    this.name = name;
    this.value = value;
  }

  public void setID(int id){
    this.id = id;
  }

  public int getID(){
    return this.id;
  }

  public void setDate(String date){
    this.date = date;
  }

  public String getDate(){
    return this.date;
  }

  public void setIDDep(int idDep){
    this.idDep = idDep;
  }

  public int getIDDep(){
    return this.idDep;
  }

  public void setParty(String party){
    this.party = party;
  }

  public String getParty(){
    return this.party;
  }

  public void setState(String state){
    this.state = state;
  }

  public String getState(){
    return this.state;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getName(){
    return this.name;
  }

  public void setValue(double value){
    this.value = value;
  }

  public double getValue(){
    return this.value;
  }
}
