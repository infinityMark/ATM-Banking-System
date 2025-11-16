public enum TransferRemark {
    Daily_Life("Rent/Mortgage Payment", "Utility Bills", "Daily Living Expenses"),
    Business_Transactions("Payment of Goods", "Service Fees", "Project Cooperation Funds"),
    Other_Common_Uses("Charitable donations", "Insurance expenses", "Transportation expenses (gas, tickets, etc.)"),
    Shopping_And_Consumption("Online Shopping Payments", "In-Store Purchases", "Electronic Product Purchases"),
    Social_Relationships("Holiday Gifts", "Loans from Friends", "Gifts and Red Envelopes"),
    Investment_And_Financial_Management("Stock Investment", "Fund Subscriptions", "Financial Product Purchases"),
    Education_And_Training("Tuition Payments", "Training Course Fees", "Book and Material Purchases"),
    Healthcare("Medical Expenses", "Medication Purchases", "Physical Examination Fees"),
    Entertainment_And_Leisure("Travel Expenses", "Restaurant Dining", "Movie Ticket Purchases");

    // Add
    private final String optionOne;
    private final String optionTwo;
    private final String optionThree;

    TransferRemark(String reasonOne, String reasonTwo, String reasonThree) {
        optionOne = reasonOne;
        optionTwo = reasonTwo;
        optionThree = reasonThree;
    }

    /**
     * Displays all options from the TransferRemark enum, with underscores in their
     * names replaced by spaces.
     * Each option is numbered sequentially starting from 1.
     */
    public static void transferRemarkFullOption() {
        int i = 1;
        // Iterate through all enum elements in TransferRemark, replace underscores with
        // spaces in their names,
        // and print each option with a sequential number
        for (TransferRemark remark : TransferRemark.values())
            System.out.println(i++ + "." + remark.name().replace('_', ' '));
    }

    public static String[] transferRemarkFullOptions() {
        int i = 1;
        String[] getTransferRemarkFullOptions = new String[8];
        // Iterate through all enum elements in TransferRemark, replace underscores with
        // spaces in their names,
        // and print each option with a sequential number
        for (TransferRemark remark : TransferRemark.values()){
            getTransferRemarkFullOptions[i] = i++ + "." + remark.name().replace('_', ' ');
        }
        return getTransferRemarkFullOptions;
    }

    /**
     * Displays sub-options corresponding to a user-selected main TransferRemark
     * option.
     * 
     * @param option The index (1-based) of the main option selected by the user
     */
    public static void transferRemarkOptionDetail(int option) {
        // Retrieve all TransferRemark enum elements and get the user-selected main
        // option
        TransferRemark[] remark = TransferRemark.values();
        TransferRemark thirdRemark = remark[option - 1];
        // Print the sub-options for the selected main option, including a "keep major
        // category only" option
        System.out.printf("1. %s\n2. %s\n3. %s\n4. %s\n", thirdRemark.optionOne, thirdRemark.optionTwo,
                thirdRemark.optionThree, "keep major category only.");
    }

    /**
     * Displays sub-options corresponding to a user-selected main TransferRemark
     * option.
     *
     * @param option The index (1-based) of the main option selected by the user
     */
    public static String[] transferRemarkOptionDetails(int option) {
        // Retrieve all TransferRemark enum elements and get the user-selected main
        // option
        String[] getTransferRemarkFullOptions = new String[2];
        TransferRemark[] remark = TransferRemark.values();
        TransferRemark thirdRemark = remark[option - 1];
        // Print the sub-options for the selected main option, including a "keep major
        // category only" option
        getTransferRemarkFullOptions[0] = "0. "+thirdRemark.optionOne;
        getTransferRemarkFullOptions[1] = "1. "+thirdRemark.optionTwo;
        getTransferRemarkFullOptions[2] = "2. "+thirdRemark.optionThree;

        return getTransferRemarkFullOptions;
    }

    /**
     * Displays the user's selected remarks (both main category and sub-option) with
     * a given title.
     * 
     * @param title      The title to display above the selected remarks
     * @param remarkList An array where:
     *                   - remarkList[0] is the 1-based index of the selected main
     *                   TransferRemark option
     *                   - remarkList[1] is the 1-based index of the selected
     *                   sub-option
     */
    public static void displayRemarkSelection(String title, int[] remarkList) {
        // Retrieve the main TransferRemark option based on the user's selection
        TransferRemark[] remark = TransferRemark.values();
        TransferRemark thirdRemark = remark[remarkList[0] - 1];

        System.out.println("\n" + title);
        // Print the main category (with underscores replaced by spaces)
        System.out.printf("%s \n", thirdRemark.name().replace('_', ' '));
        // Print the selected sub-option based on the user's choice in remarkList[1]
        System.out.println((remarkList[1] == 1) ? thirdRemark.optionOne
                : (remarkList[1] == 2) ? thirdRemark.optionTwo : (remarkList[1] == 3) ? thirdRemark.optionThree : "\n");
    }
}