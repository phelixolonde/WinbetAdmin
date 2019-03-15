package com.automata.winbetadmin;

class Model
{
  private String body;
  private Long time;
  private String title;
  
  public Model() {}
  
  public Model(String paramString1, String paramString2, Long paramLong)
  {
    this.title = paramString1;
    this.body = paramString2;
    this.time = paramLong;
  }
  
  public String getBody()
  {
    return this.body;
  }
  
  public Long getTime()
  {
    return this.time;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setBody(String paramString)
  {
    this.body = paramString;
  }
  
  public void setTime(Long paramLong)
  {
    this.time = paramLong;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
}


/* Location:              C:\Users\HANSEN\Documents\WinbetAdmin\classes-dex2jar.jar!\com\hansen\winbetadmin\Model.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */