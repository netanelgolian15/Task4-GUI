import java.util.List;

public class GUIProtocol {

    private List<Client> clients;

    public GUIProtocol(List<Client> clients) {
        this.clients = clients;
    }

    public String processInput(String theInput) {
        if (theInput == null) return "200";

        String[] items = theInput.split("#");
        if (items.length < 4) return "200";

        String name = items[0];
        String idStr = items[1];
        String itemType = items[2];
        String quantityStr = items[3];

        if (name.isEmpty() || idStr.isEmpty()) return "200";

        int id;
        int qty;
        try {
            id = Integer.parseInt(idStr);
            qty = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            return "200";
        }

        if (qty < 0) return "202";

        synchronized (clients) {
            Client foundClient = null;
            for (Client c : clients) {
                if (c.getBuisness_number() == id) {
                    foundClient = c;
                    break;
                }
            }

            if (foundClient != null) {
                if (!foundClient.getBuisness_name().equals(name)) {
                    return "201";
                }
                updateClient(foundClient, itemType, qty);
            } else {
                Client newClient = new Client();
                newClient.setBuisness_name(name);
                newClient.setBuisness_number(id);
                updateClient(newClient, itemType, qty);
                clients.add(newClient);
            }
        }
        return "100";
    }

    private void updateClient(Client c, String type, int qty) {
        if (type.equals("1")) c.setSunglasses(c.getSunglasses() + qty);
        else if (type.equals("2")) c.setBelt(c.getBelt() + qty);
        else if (type.equals("3")) c.setScarf(c.getScarf() + qty);
    }
}