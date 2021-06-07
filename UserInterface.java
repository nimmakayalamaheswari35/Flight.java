import java.io.*;

import java.util.*;


public class UserInterface {
	
	
	//verifying the new register for existing
	public static boolean verityInfoOfNewRegister(List<UserDetails> user,String phNum) {
		
		for(UserDetails d:user) {
			if(d.getMobNum().equals(phNum)) return false;
		}
		return true;
		
		
	}	
	
	
	public static Travels bookTicket(AllFlights flight) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String name, age, flightName, from, to, startTime, endTime, price;
		try {
			System.out.println("enter name:");
			name = br.readLine();
			System.out.println("enter age:");
			age = br.readLine();
			flightName = flight.getFlightName();
			from = flight.getFrom();
			to = flight.getTo();
			startTime = flight.getStartTime();
			endTime = flight.getEndTime();
			price = flight.getPrice();
			Travels journey = new Travels(name, age, flightName, from, to, startTime, endTime, price);
			System.out.println("booking your ticket...");
			try {
				Thread.sleep(1000);
				System.out.println("ticket booked successfully...!\n");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return journey;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error occured, try again...");
			return null;
		}
		
	}
	
	public static UserDetails userRegistration(List<UserDetails> userInfo) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String name,mobNum,govId,dob,email,password;
		try {
			
			System.out.println("enter name:");
			name = br.readLine();
			System.out.println("enter mobilenumber:");
			mobNum = br.readLine();
			System.out.println("enter govidNum:");
			govId = br.readLine();
			System.out.println("enter dob(DD/MM/YYYY):");
			dob = br.readLine();
			System.out.println("enter emailId:");
			email = br.readLine();
			System.out.println("enter Password:");
			password = br.readLine();
			
			if(verityInfoOfNewRegister(userInfo,mobNum)) {
				return new UserDetails(name,mobNum,govId,dob,email,password);
			}
			else {
				System.out.println("you are already a user\nPlease try login");
			}
		} catch(IOException e) {
			System.out.println("Invalid entry..");
		}
		return null;
	}

	
	
	
	
	// login the user
	
	private static int loginUser(List<UserDetails> userInfo,String mobNum, String password) {
		
		for(int i=0;i<userInfo.size();i++) {
			if(userInfo.get(i).getMobNum().equals(mobNum) && userInfo.get(i).getPassword().equals(password)) return i;
		}
		System.out.println("Not valid user\nTry register....");
		return -1;
	}
	
	
	
	
	// main method logic...
	
	
	public static void main(String[] args) {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		List<UserDetails> userInfo = new ArrayList<>();
		
		List<AllFlights> allFlights = new ArrayList<>();  
		
		allFlights.add(new AllFlights("Indigo","hyderabad","vijayawada","1:00pm","4:30pm","3499"));
		allFlights.add(new AllFlights("Redbus","hyderabad","mumbai","13:00","24:00","9900"));
		allFlights.add(new AllFlights("KingFisher","chennai","mumbai","8:40","3:20","2000"));
		allFlights.add(new AllFlights("Versa","bangalore","hyderabad","19:45","23:50","35500"));
		allFlights.add(new AllFlights("Truejet","andaman","srilanka","22:25","12:50","100000"));
		allFlights.add(new AllFlights("GoInia","Chennai","hyderabad","4:00","10:30","3800"));
		allFlights.add(new AllFlights("kingfisher","hyderabad","goa","15:20","23:40","10000"));
		
		int choice  = 0;
		do {
			System.out.println("\n1.Register\n2.Login\n3.Exit");
			try {
				choice = Integer.parseInt(br.readLine());
			} catch (NumberFormatException | IOException e) {
				System.out.println("Invalid entry\nenter valid choice..");
				continue;
			}
			switch(choice) {
			case 1: {
				
				UserDetails adding = userRegistration(userInfo);
				if(adding != null) {
					userInfo.add(adding);
				}
			}
			break;
			case 2: {
				String mobNum,password;
				try {
					System.out.println("Enter MobileNumber:");
					mobNum = br.readLine();
					System.out.println("Enter Password:");
					password = br.readLine();
					int index = loginUser(userInfo,mobNum,password);
					if(index != -1) {
						int userChoice = 0;
						do {
							System.out.println("\n1.BookTicket\n2.History\n3.Exit");
							try {
								userChoice = Integer.parseInt(br.readLine());
								switch(userChoice) {
								case 1: {
									int i=1;
										String from,to;
										System.out.println("From...");
										from = br.readLine().toLowerCase();
										System.out.println("To");
										to = br.readLine().toLowerCase();
										boolean noFlight = true;
										List<AllFlights> availbleFlights = new ArrayList<>();
									for(AllFlights list:allFlights) {
										if(list.getFrom().equals(from) && list.getTo().equals(to)) {
											System.out.println(i++ +"-->"+ list);
											availbleFlights.add(list);
											noFlight = false;
										}
									}
									if(noFlight) {
										System.out.println("no flights found in the route?");
										continue;
									}
									
									int flightChoice= Integer.parseInt(br.readLine());
									if(flightChoice>0 && flightChoice<=availbleFlights.size()) {
										Travels userJourney = bookTicket(availbleFlights.get(flightChoice-1));
										userInfo.get(index).setFlightBookings(userJourney);
									}
									else {
										System.out.println("entry wrong...");
										continue;
									}
									
								}
								break;
								case 2: {
									if(userInfo.get(index).getFlightBookings().size()==0) {
										System.out.println("no recent booking....!");
										continue;
									}
									for(int i=0;i<userInfo.get(index).getFlightBookings().size();i++) {
										System.out.println(userInfo.get(index).getFlightBookings().get(i));
									}
								}
								break;
								case 3:	System.out.println("Logging you out..."); 
									break;
										
								default: System.out.println("invalid entry made...");
									break;
								}
							}
							catch(IOException e) {
								System.out.println("Invalid entry"); 
								continue;
							}
						}while(userChoice != 3);
					}
					
				} catch(IOException e) {
					
				}
			}
			break;
			case 3: System.out.println("Exiting...");
				break;
			default: System.out.println("Invalid choice");
			}
			
		}while(choice != 3);
		
		
	}
 }

class AllFlights {
		private String flightName,from,to,startTime,endTime,price;
	
		public AllFlights(String flightName, String from, String to, String startTime, String endTime, String price) {
			super();
			this.flightName = flightName;
			this.from = from;
			this.to = to;
			this.startTime = startTime;
			this.endTime = endTime;
			this.price = price;
		}


		public String toString() {
			return "ListOfFlights [flightName=" + flightName + ", from=" + from + ", to=" + to + ", startTime=" + startTime
					+ ", endTime=" + endTime + ", price=" + price + "]";
		}




		public String getFlightName() {
			return flightName;
		}

		public void setFlightName(String flightName) {
			this.flightName = flightName;
		}

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public String getTo() {
			return to;
		}

		public void setTo(String to) {
			this.to = to;
		}

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}
}




 class Travels {
	private String name,age,flightName,from,to,startTime,endTime,price;
	
	

	public Travels(String name, String age, String flightName, String from, String to, String startTime, String endTime,
			String price) {
		super();
		this.name = name;
		this.age = age;
		this.flightName = flightName;
		this.from = from;
		this.to = to;
		this.startTime = startTime;
		this.endTime = endTime;
		this.price = price;
	}

	@Override
	public String toString() {
		return "Journey [name=" + name + ", age=" + age + ", flightName=" + flightName + ", from=" + from + ", to=" + to
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", price=" + price + "]";
	}






	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getAge() {
		return age;
	}



	public void setAge(String age) {
		this.age = age;
	}



	public String getFlightName() {
		return flightName;
	}



	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}



	public String getFrom() {
		return from;
	}



	public void setFrom(String from) {
		this.from = from;
	}



	public String getTo() {
		return to;
	}



	public void setTo(String to) {
		this.to = to;
	}



	public String getStartTime() {
		return startTime;
	}



	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}



	public String getEndTime() {
		return endTime;
	}



	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}



	public String getPrice() {
		return price;
	}



	public void setPrice(String price) {
		this.price = price;
	}

	
	

}







 class UserDetails {
	private String name,mobileNumber,govId,dob,emailId,password;
	private List<Travels> travels = new ArrayList<>();
	public UserDetails(String name, String mobNum, String govId, String dob, String email, String password) {
		super();
		this.name = name;
		this.mobileNumber = mobNum;
		this.govId = govId;
		this.dob = dob;
		this.emailId = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobNum() {
		return mobileNumber;
	}

	public void setMobNum(String mobNum) {
		this.mobileNumber = mobNum;
	}

	public String getGovId() {
		return govId;
	}

	public void setGovId(String govId) {
		this.govId = govId;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return emailId;
	}

	public void setEmail(String email) {
		this.emailId = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Travels> getFlightBookings() {	
		return travels;
	}

	public void setFlightBookings(Travels journey) {
		this.travels.add(journey);
	}
	
	
}