package sk.uniza.fri.II008.s3.simulation.messages;

import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.uniza.fri.II008.s3.model.Storage;

public class StorageMessage extends MessageForm
{
	private final Storage storage;

	public StorageMessage(Simulation simulation, Storage storage)
	{
		super(simulation);

		this.storage = storage;
	}

	public Storage getStorage()
	{
		return storage;
	}
}
